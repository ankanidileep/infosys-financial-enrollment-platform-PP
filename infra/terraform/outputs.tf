output "cluster_name" { value = module.eks.cluster_name }
output "vpc_id" { value = module.vpc.vpc_id }
output "ecr_repositories" { value = [for r in aws_ecr_repository.services : r.repository_url] }
