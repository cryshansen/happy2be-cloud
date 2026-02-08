#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Tue Mar 25 17:05:42 2025

@author: crystalhansen
"""

The bert model files are incoompattible or thhere  is an issue  with it where java includes cant parse it needs the .pt file
Was able to convert it to a python version and will test an app package api for java to consume. 

TBD there is an  onyx folder but  if python works for now  we go  there. :)

#Downloading filees for python
open spyder and download
pip install fastapi uvicorn transformers torch boto3

ERROR: pip's dependency resolver does not currently take into account all the packages that are installed. This behaviour is the source of the following dependency conflicts.
jupyter-server 1.23.4 requires anyio<4,>=3.1.0, but you have anyio 4.9.0 which is incompatible.
aiobotocore 2.5.0 requires botocore<1.29.77,>=1.29.76, but you have botocore 1.37.20 which is incompatible.

Deleted he model files as they were a repo not the model tbd try to download again later. 

Worksheets structure:

For all user engagement that requires workbooks or thought journaling, we use this endpoint

responses follow the responses{fieldname:value,} pairing for one field textual management

curl -X POST http://localhost:8383/api/workbooks/worksheet \
>   -H "Content-Type: application/json" \
>   -d '{
>     "worksheetSlug": "stress-diary",
>     "responses": {
>       "stressor": "Unexpected meeting",
>       "impact": "Heart racing",
>       "stress": 8,
>       "copingStrategy": "Breathing",
>       "diary": "Felt calmer later"
>     }
>   }'

DailyCheckin

curl -X POST http://localhost:8383/api/workbooks/worksheet \
   -H "Content-Type: application/json" \
   -d '{
  "worksheetSlug": "daily-checkin",
  "responses": {
    "userText": "I keep thinking I’m failing at work",
    "detectedDistortions": [
      {
        "slug": "all-or-nothing-thinking",
        "keywords": ["always", "never"]
      }
    ],
    "aiSummary": "You expressed concerns that triggered several thinking patterns.",
    "gratitude": "I’m grateful for my partner’s support",
    "streakAtSave": 5,
    "wellnessScore": 82
  }
}'
