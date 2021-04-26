# springboot-aws-credentialsproviders

# Removing large files from git history:

## Get BFG
wget https://repo1.maven.org/maven2/com/madgag/bfg/1.14.0/bfg-1.14.0.jar

## Repack repo
git gc

## Remove files larger than 20M
java -jar bfg-1.14.0.jar --strip-blobs-bigger-than 20M  .

# Clean up the files
git reflog expire --expire=now --all && git gc --prune=now --aggressive




