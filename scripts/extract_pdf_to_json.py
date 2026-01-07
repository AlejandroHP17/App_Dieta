#!/usr/bin/env python3
"""
Script para extraer datos del PDF ALIMENTOS_SMAE.pdf y convertirlos a JSON.

Requisitos:
    pip install PyPDF2 pandas tabula-py

Uso:
    python extract_pdf_to_json.py
"""

import json
import sys
import os

# Agregar el directorio raíz al path
sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

try:
    import PyPDF2
    from tabula import read_pdf
    import pandas as pd
except ImportError:
    print("Error: Necesitas instalar las dependencias:")
    print("pip install PyPDF2 pandas tabula-py")
    sys.exit(1)

def extract_tables_from_pdf(pdf_path):
    """Extrae tablas del PDF usando tabula."""
    try:
        # Leer todas las tablas del PDF
        tables = read_pdf(pdf_path, pages='all', multiple_tables=True)
        return tables
    except Exception as e:
        print(f"Error al leer PDF con tabula: {e}")
        return []

def convert_table_to_json(tables):
    """Convierte las tablas de pandas a formato JSON."""
    foods = []
    
    for table in tables:
        if table is None or table.empty:
            continue
            
        # Procesar cada fila de la tabla
        for _, row in table.iterrows():
            try:
                # Mapear las columnas según la estructura del PDF
                # Ajusta estos nombres según las columnas reales del PDF
                food = {
                    "id": 0,
                    "category": str(row.get("category", "")).strip() if pd.notna(row.get("category")) else "",
                    "food": str(row.get("ALIMENTOS", row.get("food", ""))).strip() if pd.notna(row.get("ALIMENTOS", row.get("food"))) else "",
                    "suggestedQuantity": float(row.get("Cantidad sugerida", row.get("suggestedQuantity", 0))) if pd.notna(row.get("Cantidad sugerida", row.get("suggestedQuantity"))) else 0.0,
                    "unit": str(row.get("Unidad", row.get("unit", ""))).strip() if pd.notna(row.get("Unidad", row.get("unit"))) else "",
                    "netWeightG": str(row.get("Peso neto (g)", row.get("netWeightG", ""))).strip() if pd.notna(row.get("Peso neto (g)", row.get("netWeightG"))) else "0",
                    "roundedGrossWeightG": int(row.get("Peso bruto redondeado (g)", row.get("roundedGrossWeightG", 0))) if pd.notna(row.get("Peso bruto redondeado (g)", row.get("roundedGrossWeightG"))) else 0,
                    "energyKcal": int(row.get("Energía (Kcal)", row.get("energyKcal", 0))) if pd.notna(row.get("Energía (Kcal)", row.get("energyKcal"))) else 0,
                    "proteinG": float(row.get("Proteína (g)", row.get("proteinG"))) if pd.notna(row.get("Proteína (g)", row.get("proteinG"))) else None,
                    "lipidsG": float(row.get("Lípidos (g)", row.get("lipidsG"))) if pd.notna(row.get("Lípidos (g)", row.get("lipidsG"))) else None,
                    "carbohydratesG": float(row.get("Hidratos de carbono (g)", row.get("carbohydratesG"))) if pd.notna(row.get("Hidratos de carbono (g)", row.get("carbohydratesG"))) else None,
                    "fiverG": float(row.get("Fibra (g)", row.get("fiverG"))) if pd.notna(row.get("Fibra (g)", row.get("fiverG"))) else None,
                    "vitaminAUgRe": float(row.get("Vitamina A (μg RE)", row.get("vitaminAUgRe"))) if pd.notna(row.get("Vitamina A (μg RE)", row.get("vitaminAUgRe"))) else None,
                    "ascorbicAcidMg": float(row.get("Ácido Ascórbico (mg)", row.get("ascorbicAcidMg"))) if pd.notna(row.get("Ácido Ascórbico (mg)", row.get("ascorbicAcidMg"))) else None,
                    "folicAcidUg": float(row.get("Ácido Fólico (μg)", row.get("folicAcidUg"))) if pd.notna(row.get("Ácido Fólico (μg)", row.get("folicAcidUg"))) else None,
                    "ironNoHemMg": float(row.get("Hierro NO HEM (mg)", row.get("ironNoHemMg"))) if pd.notna(row.get("Hierro NO HEM (mg)", row.get("ironNoHemMg"))) else None,
                    "potassiumMg": float(row.get("Potasio (mg)", row.get("potassiumMg"))) if pd.notna(row.get("Potasio (mg)", row.get("potassiumMg"))) else None,
                    "hypoglycemicIndex": float(row.get("Índice glicémico", row.get("hypoglycemicIndex"))) if pd.notna(row.get("Índice glicémico", row.get("hypoglycemicIndex"))) else None,
                    "hypoglycemicLoad": float(row.get("Carga glicémica", row.get("hypoglycemicLoad"))) if pd.notna(row.get("Carga glicémica", row.get("hypoglycemicLoad"))) else None,
                    "sugarPerEquivalentG": None,
                    "calciumMg": None,
                    "ironMg": None,
                    "sodiumMg": None,
                    "cholesterolMg": None,
                    "seleniumMg": None,
                    "seleniumUg": None,
                    "phosphorusMg": None,
                    "agSaturatedG": None,
                    "agMonounsaturatedG": None,
                    "agPolyunsaturatedG": None
                }
                
                # Solo agregar si tiene nombre de alimento
                if food["food"] and food["food"] != "nan":
                    foods.append(food)
            except Exception as e:
                print(f"Error procesando fila: {e}")
                continue
    
    return foods

def main():
    pdf_path = os.path.join(os.path.dirname(os.path.dirname(os.path.abspath(__file__))), "ALIMENTOS_SMAE.pdf")
    output_path = os.path.join(os.path.dirname(os.path.dirname(os.path.abspath(__file__))), "app/src/main/assets/alimentos_smae.json")
    
    if not os.path.exists(pdf_path):
        print(f"Error: No se encontró el archivo PDF en: {pdf_path}")
        sys.exit(1)
    
    print(f"Extrayendo datos de: {pdf_path}")
    tables = extract_tables_from_pdf(pdf_path)
    
    if not tables:
        print("No se pudieron extraer tablas del PDF. Intentando método alternativo...")
        # Aquí podrías implementar un método alternativo
        return
    
    print(f"Se encontraron {len(tables)} tablas")
    foods = convert_table_to_json(tables)
    
    print(f"Se extrajeron {len(foods)} alimentos")
    
    # Guardar en JSON
    with open(output_path, 'w', encoding='utf-8') as f:
        json.dump(foods, f, ensure_ascii=False, indent=2)
    
    print(f"✅ JSON guardado en: {output_path}")
    print(f"Total de alimentos: {len(foods)}")

if __name__ == "__main__":
    main()

