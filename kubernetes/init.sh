kubectl create namespace coffee-shop

kubectl apply -f infra
kubectl apply -f backend
kubectl apply -f ingress.yaml
