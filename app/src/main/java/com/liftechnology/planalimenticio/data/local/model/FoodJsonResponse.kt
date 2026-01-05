package com.liftechnology.planalimenticio.data.local.model

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import java.lang.reflect.Type

/**
 * Deserializador seguro que maneja valores numéricos como String o Number,
 * limpiando formatos inválidos (como "1.4." o múltiples puntos).
 */
class SafeNumberDeserializer : JsonDeserializer<Number?> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Number? {
        if (json == null || json.isJsonNull) return null
        
        return try {
            when {
                json.isJsonPrimitive -> {
                    val primitive = json.asJsonPrimitive
                    when {
                        primitive.isNumber -> primitive.asNumber
                        primitive.isString -> {
                            val str = primitive.asString.trim()
                            if (str.isEmpty() || str.equals("null", ignoreCase = true)) {
                                null
                            } else {
                                // Limpiar: remover caracteres no numéricos excepto punto y signo negativo
                                var cleaned = str.replace(Regex("[^0-9.\\-]"), "")
                                // Si termina con punto, removerlo
                                cleaned = cleaned.trimEnd('.')
                                // Si tiene múltiples puntos consecutivos o separados, tomar solo el primero
                                val dotIndex = cleaned.indexOf('.')
                                if (dotIndex >= 0) {
                                    val beforeDot = cleaned.substring(0, dotIndex)
                                    val afterDot = cleaned.substring(dotIndex + 1).replace(".", "")
                                    cleaned = "$beforeDot.$afterDot"
                                }
                                cleaned.toDoubleOrNull() ?: cleaned.toIntOrNull() ?: 0.0
                            }
                        }
                        else -> null
                    }
                }
                else -> null
            }
        } catch (e: Exception) {
            null
        }
    }
}

/**
 * Deserializador seguro para Float que maneja valores inválidos.
 */
class SafeFloatDeserializer : JsonDeserializer<Float?> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Float? {
        if (json == null || json.isJsonNull) return null
        
        return try {
            when {
                json.isJsonPrimitive -> {
                    val primitive = json.asJsonPrimitive
                    when {
                        primitive.isNumber -> primitive.asFloat
                        primitive.isString -> {
                            val str = primitive.asString.trim()
                            if (str.isEmpty() || str.equals("null", ignoreCase = true)) {
                                null
                            } else {
                                // Limpiar el string
                                var cleaned = str.replace(Regex("[^0-9.\\-]"), "")
                                cleaned = cleaned.trimEnd('.')
                                val dotIndex = cleaned.indexOf('.')
                                if (dotIndex >= 0) {
                                    val beforeDot = cleaned.substring(0, dotIndex)
                                    val afterDot = cleaned.substring(dotIndex + 1).replace(".", "")
                                    cleaned = "$beforeDot.$afterDot"
                                }
                                cleaned.toFloatOrNull()
                            }
                        }
                        else -> null
                    }
                }
                else -> null
            }
        } catch (e: Exception) {
            null
        }
    }
}

/**
 * Modelo de respuesta para parsear el JSON de alimentos del SMAE.
 * Mapea los campos en español del JSON a las propiedades de la entidad.
 * 
 * @author pelkidev
 */
data class FoodJsonResponse(
    @SerializedName("Alimento")
    val alimento: String,
    
    @SerializedName("Cantidad sugerida")
    @JsonAdapter(SafeNumberDeserializer::class)
    val cantidadSugerida: Number?, // Puede ser Int o Float
    
    @SerializedName("Unidad")
    val unidad: String,
    
    @SerializedName("Peso bruto redondeado (g)")
    @JsonAdapter(SafeNumberDeserializer::class)
    val pesoBrutoRedondeado: Number?,
    
    @SerializedName("Peso neto (g)")
    @JsonAdapter(SafeNumberDeserializer::class)
    val pesoNeto: Number?, // Puede ser Int o Float
    
    @SerializedName("Energía (Kcal)")
    @JsonAdapter(SafeNumberDeserializer::class)
    val energiaKcal: Number?,
    
    @SerializedName("Proteína (g)")
    @JsonAdapter(SafeFloatDeserializer::class)
    val proteina: Float?,
    
    @SerializedName("Lípidos (g)")
    @JsonAdapter(SafeFloatDeserializer::class)
    val lipidos: Float?,
    
    @SerializedName("Hidratos de carbono (g)")
    @JsonAdapter(SafeFloatDeserializer::class)
    val hidratosCarbono: Float?,
    
    @SerializedName("Fibra (g)")
    @JsonAdapter(SafeFloatDeserializer::class)
    val fibra: Float?,
    
    @SerializedName("Vitamina A (µg RE)")
    @JsonAdapter(SafeFloatDeserializer::class)
    val vitaminaA: Float?,
    
    @SerializedName("Acido Ascórbico (mg)")
    @JsonAdapter(SafeFloatDeserializer::class)
    val acidoAscorbico: Float?,
    
    @SerializedName("Ácido Fólico (µg)")
    @JsonAdapter(SafeFloatDeserializer::class)
    val acidoFolico: Float?,
    
    @SerializedName("Hierro NO HEM (mg)")
    @JsonAdapter(SafeFloatDeserializer::class)
    val hierroNoHem: Float?,
    
    @SerializedName("Potasio (mg)")
    @JsonAdapter(SafeFloatDeserializer::class)
    val potasio: Float?,
    
    @SerializedName("Índice glicémico")
    @JsonAdapter(SafeNumberDeserializer::class)
    val indiceGlicemico: Number?,
    
    @SerializedName("Carga glicémica")
    @JsonAdapter(SafeNumberDeserializer::class)
    val cargaGlicemica: Number?,
    
    @SerializedName("Categoria")
    val categoria: String,
    
    @SerializedName("Azúcar por equivalente (g)")
    @JsonAdapter(SafeFloatDeserializer::class)
    val azucarPorEquivalente: Float?,
    
    @SerializedName("Calcio (mg)")
    @JsonAdapter(SafeFloatDeserializer::class)
    val calcio: Float?,
    
    @SerializedName("Hierro (mg)")
    @JsonAdapter(SafeFloatDeserializer::class)
    val hierro: Float?,
    
    @SerializedName("sodio (mg)")
    @JsonAdapter(SafeFloatDeserializer::class)
    val sodio: Float?,
    
    @SerializedName("Colesterol (mg)")
    @JsonAdapter(SafeFloatDeserializer::class)
    val colesterol: Float?,
    
    @SerializedName("Selenio (mg)")
    @JsonAdapter(SafeFloatDeserializer::class)
    val selenioMg: Float?,
    
    @SerializedName("Fósforo (mg)")
    @JsonAdapter(SafeFloatDeserializer::class)
    val fosforo: Float?,
    
    @SerializedName("AG Saturados (g)")
    @JsonAdapter(SafeFloatDeserializer::class)
    val agSaturados: Float?,
    
    @SerializedName("AG\nMonoinsaturado s (g)")
    @JsonAdapter(SafeFloatDeserializer::class)
    val agMonoinsaturados: Float?,
    
    @SerializedName("AG\nPolinsaturados (g)")
    @JsonAdapter(SafeFloatDeserializer::class)
    val agPolinsaturados: Float?
) {
    /**
     * Convierte el modelo JSON a FoodEntity para Room.
     */
    fun toFoodEntity(): com.liftechnology.planalimenticio.data.local.entity.FoodEntity {
        return com.liftechnology.planalimenticio.data.local.entity.FoodEntity(
            id = 0, // Se auto-genera
            category = categoria,
            food = alimento,
            suggestedQuantity = when (cantidadSugerida) {
                is Float -> cantidadSugerida
                is Double -> cantidadSugerida.toFloat()
                is Int -> cantidadSugerida.toFloat()
                is Long -> cantidadSugerida.toFloat()
                null -> 0f
                else -> cantidadSugerida.toFloat()
            },
            unit = unidad,
            netWeightG = pesoNeto?.toString() ?: "0",
            roundedGrossWeightG = when (pesoBrutoRedondeado) {
                is Int -> pesoBrutoRedondeado
                is Double -> pesoBrutoRedondeado.toInt()
                is Float -> pesoBrutoRedondeado.toInt()
                is Long -> pesoBrutoRedondeado.toInt()
                null -> 0
                else -> pesoBrutoRedondeado.toInt()
            },
            energyKcal = when (energiaKcal) {
                is Int -> energiaKcal
                is Double -> energiaKcal.toInt()
                is Float -> energiaKcal.toInt()
                is Long -> energiaKcal.toInt()
                null -> 0
                else -> energiaKcal.toInt()
            },
            proteinG = proteina,
            lipidsG = lipidos,
            carbohydratesG = hidratosCarbono,
            fiverG = fibra,
            vitaminAUgRe = vitaminaA,
            ascorbicAcidMg = acidoAscorbico,
            folicAcidUg = acidoFolico,
            ironNoHemMg = hierroNoHem,
            potassiumMg = potasio,
            hypoglycemicIndex = when (indiceGlicemico) {
                is Float -> indiceGlicemico
                is Double -> indiceGlicemico.toFloat()
                is Int -> indiceGlicemico.toFloat()
                is Long -> indiceGlicemico.toFloat()
                null -> null
                else -> indiceGlicemico.toFloat()
            },
            hypoglycemicLoad = when (cargaGlicemica) {
                is Float -> cargaGlicemica
                is Double -> cargaGlicemica.toFloat()
                is Int -> cargaGlicemica.toFloat()
                is Long -> cargaGlicemica.toFloat()
                null -> null
                else -> cargaGlicemica.toFloat()
            },
            sugarPerEquivalentG = azucarPorEquivalente,
            calciumMg = calcio,
            ironMg = hierro,
            sodiumMg = sodio,
            cholesterolMg = colesterol,
            seleniumMg = selenioMg,
            seleniumUg = null, // No está en el JSON
            phosphorusMg = fosforo,
            agSaturatedG = agSaturados,
            agMonounsaturatedG = agMonoinsaturados,
            agPolyunsaturatedG = agPolinsaturados
        )
    }
}

