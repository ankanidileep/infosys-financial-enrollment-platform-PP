# Training business rules

The original project requirements included customer/account segmentation. For the practical implementation the tier calculation is simplified to asset value:

| Tier | Asset value |
|---|---:|
| SPWS | >= 10,000,000 |
| SPCS/PINN | 1,000,000 – 9,999,999.99 |
| PLAT | 250,000 – 999,999.99 |
| GOLD | 100,000 – 249,999.99 |
| PREF | < 100,000 |

Example:

```json
{
  "customerId": "CUST1001",
  "accountId": "ACC5001",
  "accountType": "INVESTMENT",
  "status": "OPEN",
  "assetValue": 500000
}
```

Expected segment: `PLAT`.

For the hands-on application, the seven service endpoints are intentionally simple. The DevOps architecture is the primary focus. In a larger implementation, the workflow would use synchronous REST and/or asynchronous messaging, persistent state, idempotency keys, retries with backoff, dead-letter handling, audit storage and stronger domain validation.
