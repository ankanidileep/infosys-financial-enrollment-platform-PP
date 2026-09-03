build:
	./scripts/build-all.sh

up:
	docker compose up -d --build

down:
	docker compose down

test:
	./scripts/test-api.sh

k8s:
	./scripts/deploy-k8s.sh
