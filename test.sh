#!/bin/bash
curl -d '["https://ya.ru", "https://2gis.ru/moscow", "https://vk.com/"]' -H "Content-Type: application/json" -X POST http://localhost:8080/parser | json_pp