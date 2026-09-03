#!/usr/bin/env bash
set -euo pipefail
: "${ECR_REGISTRY:?Set ECR_REGISTRY, e.g. 123456789012.dkr.ecr.us-east-1.amazonaws.com/financial-platform}"
: "${IMAGE_TAG:?Set IMAGE_TAG, e.g. 1.0.0}"
TMP_DIR=$(mktemp -d)
trap 'rm -rf "$TMP_DIR"' EXIT
cp -R deploy/k8s/. "$TMP_DIR/"
find "$TMP_DIR" -type f -name '*.yaml' -print0 | xargs -0 sed -i "s#ECR_REGISTRY#$ECR_REGISTRY#g; s#IMAGE_TAG#$IMAGE_TAG#g"
kubectl apply -f deploy/k8s/00-namespace.yaml
kubectl apply -f deploy/k8s/01-config.yaml
kubectl apply -f "$TMP_DIR/services"
kubectl apply -f deploy/k8s/90-ingress.yaml
kubectl -n financial-platform rollout status deployment/account-ingestion-service --timeout=180s
kubectl -n financial-platform get pods,svc,ingress,hpa
