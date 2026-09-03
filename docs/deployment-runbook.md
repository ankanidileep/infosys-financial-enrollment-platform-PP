# Deployment Runbook

## 1. Build and test
```bash
./scripts/build-all.sh
```
Both `java -version` and `mvn -version` must use JDK 21.

## 2. Local containers
```bash
docker compose up -d --build
docker compose ps
./scripts/test-api.sh
```
Gateway: http://localhost:8080

## 3. Terraform/EKS
```bash
cd infra/terraform
terraform init
terraform fmt -recursive
terraform validate
terraform plan
terraform apply
aws eks update-kubeconfig --region us-east-1 --name financial-platform-eks
kubectl get nodes
```

## 4. ECR
Terraform creates repositories named `financial-platform/<service>`. Set: `ECR_REGISTRY=<account>.dkr.ecr.us-east-1.amazonaws.com/financial-platform`.

## 5. Build/push images
Build all services locally with `./scripts/build-all.sh`, then build/tag/push each image to its ECR repository. The Jenkinsfile automates this in CI.

## 6. Kubernetes deployment
Set `ECR_REGISTRY` and `IMAGE_TAG`, then:
```bash
export ECR_REGISTRY=123456789012.dkr.ecr.us-east-1.amazonaws.com/financial-platform
export IMAGE_TAG=1.0.0
./scripts/deploy-k8s.sh
```

## 7. Troubleshooting
```bash
kubectl -n financial-platform get pods
kubectl -n financial-platform describe pod <pod-name>
kubectl -n financial-platform logs <pod-name> --tail=100
kubectl -n financial-platform get endpoints account-ingestion-service
```
Check service-specific ports: 8081–8087.

## 8. Rollback
```bash
kubectl -n financial-platform rollout history deployment/account-ingestion-service
kubectl -n financial-platform rollout undo deployment/account-ingestion-service
```

## 9. Cleanup
```bash
cd infra/terraform
terraform destroy
```
