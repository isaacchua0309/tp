#!/bin/bash
# This script removes single-line comments but preserves Javadoc comments
find src -name "*.java" -type f | while read file; do
  # Process only lines that are not in Javadoc blocks
