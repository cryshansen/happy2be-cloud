# 1. Use the platform flag to ensure it runs correctly on Google Cloud (Linux)
FROM --platform=linux/amd64 python:3.10-slim

# 2. Set the working directory
WORKDIR /app

# 3. Install system dependencies (needed for building AI libraries like 'transformers')
RUN apt-get update && apt-get install -y --no-install-recommends \
    build-essential \
    && rm -rf /var/lib/apt/lists/*

# 4. Copy requirements first (this speeds up builds later)
COPY requirements.txt .

# 5. Install Python libraries with a long timeout for the heavy AI models
RUN pip install --no-cache-dir --default-timeout=100 -r requirements.txt

# 6. Copy your actual code
# Since your script is named 'happyfaceflask.py', we'll rename it to 'app.py' inside the container
COPY happyfaceflask.py app.py

# 7. Expose the port
EXPOSE 5001

# 8. Start the Flask app
CMD ["python", "app.py"]