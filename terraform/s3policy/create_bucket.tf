terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 3.0"
    }
  }
}

# Configure the AWS Provider
provider "aws" {
  region  = "eu-west-1"
  profile = "mike"
}


resource "aws_s3_bucket" "bucket_policy_demo" {
  bucket = "bucket-policy-demo1"
  acl    = "private"
}

resource "aws_iam_policy" "policy" {
  name        = "mike-read-write"
  path        = "/"
  description = ""
  policy      = <<POLICY
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Sid": "VisualEditor0",
            "Effect": "Allow",
            "Action": [
                "s3:PutObject",
                "s3:GetObject",
                "s3:ListBucket",
                "s3:DeleteObjectVersion",
                "s3:DeleteObject",
                "s3:GetBucketLocation",
                "s3:GetObjectVersion"
            ],
            "Resource": [
                "arn:aws:s3:::bucket-policy-demo1",
                "arn:aws:s3:::bucket-policy-demo1/mixedstuff/demo/*"
            ]
        }
    ]
}
POLICY
}

resource "aws_iam_user" "user" {
  name = "mike-policy-demo"
  path = "/"
}

resource "aws_iam_user_policy_attachment" "attach" {
  user       = aws_iam_user.user.name
  policy_arn = aws_iam_policy.policy.arn
}

resource "aws_iam_access_key" "key" {
  user = aws_iam_user.user.name
}
output "thesecret" {
  value = aws_iam_access_key.key.secret
}
output "theid" {
  value = aws_iam_access_key.key.id
}