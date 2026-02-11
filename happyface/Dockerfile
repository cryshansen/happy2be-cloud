# 1. Use the platform flag to ensure it runs correctly on Google Cloud (Linux)
FROM --platform=linux/amd64 python:3.10-slim

# 2. Set the working directory
# Install light dependencies first
RUN pip install --no-cache-dir flask gunicorn requests numpy pandas

# Install Heavy AI libraries ONE BY ONE (Memory efficient)
RUN pip install --no-cache-dir torch --index-url https://download.pytorch.org/whl/cpu
RUN pip install --no-cache-dir triton

# Copy code and launch
COPY . .
CMD ["python", "app.py"]