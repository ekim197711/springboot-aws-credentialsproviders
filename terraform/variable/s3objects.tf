terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 3.0"
    }
  }
}

variable "mybucketname" {
  description = "The name of my bucket"
  type = string
  default = "notset"
}

variable "shoppinglist" {
  type = list(string)
  default = ["apple", "portatoes", "carrots"]
}

variable "spaceship" {
  type = object({
    captain = string
    fuel = number
  })
  default = {
    captain = "Mike"
    fuel = 100
  }
}

# Configure the AWS Provider
provider "aws" {
  region  = "eu-west-1"
  profile = "mike"
}

resource "aws_s3_bucket" "myvardemobucket" {
  bucket = var.mybucketname
}
resource "aws_s3_bucket_object" "myfile" {
  bucket = aws_s3_bucket.myvardemobucket.id
  key = var.spaceship.captain
  content = "Test 1 2 3--- ASDFASDfasdfs"
}

output "myfinalfilename" {
  value = aws_s3_bucket_object.myfile.key
}

