# Infosys Financial Enrollment Platform

Production-like hands-on DevOps reference implementation for a financial account enrollment and customer eligibility workflow. This is training code, not proprietary Infosys/Charles Schwab source code.

## Application flow
Account Management System (source) → Gateway → Account Ingestion → Data Validation → Customer Eligibility → Account Segmentation → Enrollment → Data Transformation → Transaction Notification → Transaction Processing System (target).

## Service ports
Gateway: 8080. Backend services: 8081–8087. Backend ports are not published to the host.

| Service | Port |
|---|---:|
| account-ingestion-service | 8081 |
| data-validation-service | 8082 |
| customer-eligibility-service | 8083 |
| account-segmentation-service | 8084 |
| enrollment-service | 8085 |
| data-transformation-service | 8086 |
| transaction-notification-service | 8087 |

## Training tier rules
SPWS >= $10M; SPCS/PINN $1M–$9,999,999.99; PLAT $250K–$999,999.99; GOLD $100K–$249,999.99; PREF < $100K.

## Build
The project requires JDK 21. The previous build failed because `java -version` was JDK 21 while Maven was running under JDK 17, causing `release version 21 not supported`. `scripts/build-all.sh` now selects Amazon Corretto 21 when present and verifies the toolchain.

```bash
./scripts/build-all.sh
docker compose up -d --build
docker compose ps
./scripts/test-api.sh
```

## Kubernetes
Manifests use service-specific ports, named HTTP ports, probes, resources, rolling updates, HPA, PDB and a writable `/tmp` volume because the Java containers use a read-only root filesystem. The base lab leaves NetworkPolicy empty because a blanket deny can block ALB traffic; add least-privilege policies after validating the cluster networking path.

## DevOps lifecycle
Git → Jenkins → Maven → tests → SonarQube → OWASP Dependency Check → Docker → Trivy → ECR → GitOps → Argo CD → EKS → Prometheus/Grafana.

## Repository
```text
services/ gateway/ deploy/k8s/ deploy/helm/ deploy/argocd/ infra/terraform/ monitoring/ scripts/ docs/ Jenkinsfile docker-compose.yml
```

Use this repository as a practical modernization/reference implementation. Do not present it as a copy of confidential production code.
