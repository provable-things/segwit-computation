# segwit-computation

Intended to be used with Oraclize's off-chain computation service.

Looks for the initial last `ARG0` block headers via a Bitcoin SPV node, returning the count of those last blocks signalling for segwit activation.

### Guide

Archive the files in a zip file named `archive.zip`

Upload via IPFS or other supported off-chain computation storage service

Enter the IPFS multihash returned as the archive file to use for the off-chain computation service query

