#!/bin/bash
set -e

echo "Criando bucket files..."

awslocal s3 mb s3://files

echo "Bucket filesms criado com sucesso."