# Script de Extracción de Datos del PDF

Este script ayuda a extraer los datos del PDF `ALIMENTOS_SMAE.pdf` y convertirlos a formato JSON.

## Instalación

```bash
pip install PyPDF2 pandas tabula-py
```

**Nota**: `tabula-py` requiere Java instalado en tu sistema.

## Uso

```bash
python scripts/extract_pdf_to_json.py
```

El script generará el archivo `app/src/main/assets/alimentos_smae.json` con todos los datos extraídos.

## Alternativa Manual

Si el script no funciona correctamente, puedes:

1. Abrir el PDF en Excel o Google Sheets
2. Copiar las tablas
3. Convertirlas manualmente a JSON usando la estructura del archivo `alimentos_smae.json` existente

## Estructura del JSON

Cada alimento debe tener esta estructura:

```json
{
  "id": 0,
  "category": "VERDURAS",
  "food": "Nombre del alimento",
  "suggestedQuantity": 2.0,
  "unit": "taza",
  "netWeightG": "98",
  "roundedGrossWeightG": 120,
  "energyKcal": 22,
  "proteinG": 2.2,
  "lipidsG": 0.1,
  "carbohydratesG": 4.3,
  "fiverG": 3.6,
  "vitaminAUgRe": 310.9,
  "ascorbicAcidMg": 29.5,
  "folicAcidUg": 14.8,
  "ironNoHemMg": 2.5,
  "potassiumMg": 749.8,
  "hypoglycemicIndex": 64.0,
  "hypoglycemicLoad": 2.7,
  "sugarPerEquivalentG": null,
  "calciumMg": null,
  "ironMg": null,
  "sodiumMg": null,
  "cholesterolMg": null,
  "seleniumMg": null,
  "seleniumUg": null,
  "phosphorusMg": null,
  "agSaturatedG": null,
  "agMonounsaturatedG": null,
  "agPolyunsaturatedG": null
}
```

