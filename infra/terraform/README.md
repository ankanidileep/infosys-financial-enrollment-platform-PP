# Terraform

```bash
terraform init
terraform fmt -recursive
terraform validate
terraform plan
terraform apply
```

Then:

```bash
aws eks update-kubeconfig --region us-east-1 --name financial-platform-eks
kubectl get nodes
```

For a KodeKloud-style 3-hour lab, destroy the stack when finished:

```bash
terraform destroy
```

Review the variables and cost assumptions before using a real AWS account.
