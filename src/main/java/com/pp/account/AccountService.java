package com.pp.account;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.pp.assetholding.AssetHoldingJpa;
import com.pp.assetholding.AssetHoldingModel;
import com.pp.assetholding.AssetHoldingRepository;
import com.pp.stocks.StockService;
import com.pp.stocks.price.StockPriceRepository;
import com.pp.user.UserJpa;
import com.pp.user.details.AccountCreationModel;
import com.pp.user.role.Role;
import java.util.Set;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class AccountService {

    private AccountRepository accountRepository;
    private StockService stockService;
    private AssetHoldingRepository assetHoldingRepository;
    private StockPriceRepository stockPriceRepository;

    public AccountService(AccountRepository accountRepository,
                          StockService stockService,
                          AssetHoldingRepository assetHoldingRepository,
                          StockPriceRepository stockPriceRepository) {
        this.accountRepository = accountRepository;
        this.stockService = stockService;
        this.assetHoldingRepository = assetHoldingRepository;
        this.stockPriceRepository = stockPriceRepository;
    }



    public List<AccountModel> getUserAccounts(UUID loggedInUser, Set<Role> roles, UUID userId) {
        var isAdministrator = roles.contains(Role.ADMINISTRATOR);
        if (!isAdministrator && ! loggedInUser.equals(userId)) {
            throw new IllegalArgumentException("User not owned");
        }

        var accounts = accountRepository.findAccountsByUserId(userId);

        return accounts.stream()
                .map(AccountJpa::toDomain)
                .collect(Collectors.toList());
    }


    public AccountModel createNewUserAccount(AccountCreationModel model, UserJpa user) {
        var account = AccountModel.newJpa(model,user);
        var createdAccount = accountRepository.save(account);
        return createdAccount.toDomain();
    }

    public AccountModel topup(AmountModel payload,UUID accountId, UUID userId) {
        var ownedAccount = accountRepository.findById(accountId);
        if (ownedAccount.isEmpty()){
            throw new IllegalArgumentException("Account doesn't exist.");
        }
        var accountToTopup = ownedAccount.get();
        if (!accountToTopup.getUser().getId().equals(userId)){
            throw new IllegalArgumentException("Account not owned");
        }

        accountToTopup.topupBalance(payload.getAmount());

        return accountRepository.save(accountToTopup).toDomain();
    }

    public List<AssetHoldingModel> retrieveAccountAssets(UUID accountId, UUID userId) {
        var ownedAccount = accountRepository.findById(accountId);
        if (ownedAccount.isEmpty()){
            throw new IllegalArgumentException("Account doesn't exist");
        }
        var account = ownedAccount.get();
        if (!account.getUser().getId().equals(userId)){
            throw new IllegalArgumentException("Account not owned");
        }

        var holdings = account.getHoldings().stream()
                .map( holding -> holding.toDomain())
                .collect(Collectors.toList());

        return holdings;
    }

    public AssetHoldingModel retrieveSingleAccountAsset(UUID accountId,String assetSymbol, UUID userId){
        var ownedAccount = accountRepository.findById(accountId);
        if (ownedAccount.isEmpty()){
            throw new IllegalArgumentException("Account doesn't exist");
        }
        var account = ownedAccount.get();
        if (!account.getUser().getId().equals(userId)){
            throw new IllegalArgumentException("Account not owned");
        }

        var _assetHolding = assetHoldingRepository.findByAccountIDAndAssetSymbol(accountId,assetSymbol);

        if (_assetHolding.isEmpty()) {
            throw new IllegalArgumentException("Account doesn't contain this asset");
        }

       return  _assetHolding.get().toDomain();
    }

    public AssetHoldingModel buySingleAsset(BigDecimal amountToBuy, String assetSymbol, UUID accountId, UUID userId) {
        var ownedAccount = accountRepository.findById(accountId);
        if (ownedAccount.isEmpty()){
            throw new IllegalArgumentException("Account doesn't exist");
        }
        var account = ownedAccount.get();
        if (!account.getUser().getId().equals(userId)){
            throw new IllegalArgumentException("Account not owned");
        }

        if (account.getFiatBalance().compareTo(amountToBuy)<0 || account.getFiatBalance().compareTo(BigDecimal.ZERO) == 0){
            throw new IllegalArgumentException("Not enough balance in the account!");
        }

        var _latestStockPrice = stockPriceRepository.findFirstBySymbolOrderByCreatedDate(assetSymbol);
        if (_latestStockPrice.isEmpty()) {
            throw new IllegalArgumentException(
                    String.format(" Couldn't retrieve price for symbol %s",assetSymbol)
            );
        }

        var latestStockPrice = _latestStockPrice.get().getLastSalePrice();
        var _assetHolding = assetHoldingRepository.findByAccountIDAndAssetSymbol(accountId,assetSymbol);

        AssetHoldingJpa newHolding;
        if (_assetHolding.isEmpty()) {

            //first time holding
            var firstTimeAssetHolding = AssetHoldingJpa
                    .firstTimeHolding(amountToBuy,latestStockPrice,assetSymbol,accountId);

            newHolding = assetHoldingRepository.save(firstTimeAssetHolding);

        } else {

            // existing holding
            var existingHolding = _assetHolding.get();
            var aggregatedHolding = existingHolding.aggregateExistingHolding(amountToBuy,latestStockPrice);

            newHolding = assetHoldingRepository.save(aggregatedHolding);
        }

        account.debitBalance(amountToBuy);
        accountRepository.save(account);
        return newHolding.toDomain();
    }
}
