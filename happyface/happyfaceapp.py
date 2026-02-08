#!/usr/bin/env python3
#mv ~/.cache/huggingface/hub/models--distilbert-base-uncased-finetuned-sst-2-english /Users/crystalhansen/eclipse/happy2be/models/
# -*- coding: utf-8 -*-
"""
Created on Tue Mar 25 16:34:20 2025

@author: crystalhansen
"#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""

from fastapi import FastAPI
# Load model directly
from transformers import AutoTokenizer, AutoModelForSequenceClassification, pipeline

app = FastAPI()
tokenizer = AutoTokenizer.from_pretrained("distilbert/distilbert-base-uncased-finetuned-sst-2-english")
model = AutoModelForSequenceClassification.from_pretrained("distilbert/distilbert-base-uncased-finetuned-sst-2-english")
#This also worked :)

#tokenizer = AutoTokenizer.from_pretrained(model_path)

# Create the sentiment-analysis pipeline
nlp = pipeline("sentiment-analysis", model=model, tokenizer=tokenizer)


# Test with an example sentence
result = nlp("I love this product!")

print(result)

result2 = nlp("I lost my job how will i pay rent")

print(result2)


@app.get("/")
def home():
    return {"message": "API is running"}

@app.post("/predict/")
def predict(text: str):
    result = nlp(text) #classifier(text)
    return {"label": result[0]['label'], "score": result[0]['score']}






