# Production standards used in this lab

## Reliability
- Kubernetes readiness, liveness and startup probes.
- Resource requests/limits.
- Rolling updates.
- PodDisruptionBudget.
- Horizontal Pod Autoscaler.
- Topology spread constraints.
- Retry-safe processing and correlation IDs.
- Graceful shutdown.

## Security
- Non-root containers.
- Read-only root filesystem where practical.
- Drop Linux capabilities.
- Kubernetes NetworkPolicy.
- Secrets are referenced from Kubernetes Secrets; for real production use AWS Secrets Manager + CSI/External Secrets.
- Images are scanned with Trivy before deployment.
- Dependency scanning with OWASP Dependency-Check.
- TLS should terminate at the approved ingress/load-balancer layer.
- No credentials are committed to Git.

## Observability
- Spring Boot Actuator.
- Prometheus metrics.
- Structured logs with correlation ID.
- Health endpoints.
- Grafana dashboards can be added using the supplied monitoring folder.

## AWS/EKS design
- Public subnets for load balancers.
- Private subnets for worker nodes.
- Multi-AZ node placement.
- NAT Gateway per AZ is the high-availability pattern; a learning lab may use fewer NAT gateways to reduce cost.
- IAM roles for workloads should be preferred over long-lived AWS access keys.
- Use ECR for image storage.
- Use GitOps for deployment reconciliation.

AWS's EKS guidance recommends multi-AZ clusters, private worker nodes, careful security groups, and topology-aware placement. Kubernetes documents startup/readiness/liveness probes as the mechanisms for detecting initialization and unhealthy containers.

References:
- AWS EKS Best Practices: https://docs.aws.amazon.com/eks/latest/best-practices/introduction.html
- AWS EKS VPC/subnets: https://docs.aws.amazon.com/eks/latest/best-practices/subnets.html
- Kubernetes probes: https://kubernetes.io/docs/concepts/workloads/pods/probes/
