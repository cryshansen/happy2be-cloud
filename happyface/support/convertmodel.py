from transformers import AutoModel

# This will force download the model again
model = AutoModel.from_pretrained("distilbert-base-uncased-finetuned-sst-2-english", force_download=True)

print("Model downloaded successfully!")



from transformers.utils import logging
from transformers.utils.hub import cached_file

logging.set_verbosity_info()
model_name = "distilbert-base-uncased-finetuned-sst-2-english"

model_path = cached_file(model_name, "config.json")
print("Model is stored in:", model_path)


#mv ~/.cache/huggingface/hub/models--distilbert-base-uncased-finetuned-sst-2-english /Users/crystalhansen/eclipse/happy2be/models/

#mv ~/.cache/huggingface/hub/models--distilbert-base-uncased-finetuned-sst-2-english /Users/crystalhansen/notebooks/happyface/models/
