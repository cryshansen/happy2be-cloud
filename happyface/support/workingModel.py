#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Tue Mar 25 18:39:46 2025

@author: crystalhansen
"""
import torch
from transformers import DistilBertModel
model = DistilBertModel.from_pretrained("distilbert-base-uncased", torch_dtype=torch.float16, attn_implementation="sdpa")

#successful download? it rsolved