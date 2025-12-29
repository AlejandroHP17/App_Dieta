# Instrucciones para Inicializar la Base de Datos con Datos del PDF

## 📋 Descripción

Este documento explica cómo poblar la base de datos Room con los datos del documento **ALIMENTOS_SMAE.pdf** la primera vez que se ejecuta la aplicación.

## 🚀 Cómo Funciona

El sistema de inicialización funciona de la siguiente manera:

1. **Primera ejecución**: La aplicación verifica si la base de datos está vacía
2. **Si está vacía**: Lee el archivo JSON `alimentos_smae.json` desde `assets/`
3. **Si no existe el JSON**: Usa datos hardcodeados como fallback
4. **Inserta los datos**: Pobla la base de datos con todos los alimentos

## 📁 Archivos Creados

### 1. `DatabaseInitializer.kt`
- Clase que maneja la inicialización de la base de datos
- Lee el archivo JSON desde assets
- Tiene datos hardcodeados como fallback

### 2. `InitializeDatabaseUseCase.kt`
- Caso de uso para inicializar la base de datos
- Retorna el resultado de la inicialización

### 3. `alimentos_smae.json` (en `app/src/main/assets/`)
- Archivo JSON con los datos de los alimentos
- Debes completar este archivo con todos los datos del PDF

## 📝 Pasos para Completar la Implementación

### Paso 1: Extraer Datos del PDF

Necesitas extraer todos los datos del PDF `ALIMENTOS_SMAE.pdf` y convertirlos al formato JSON.

**Estructura del JSON:**
```json
[
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
]
```

### Paso 2: Completar el Archivo JSON

1. Abre el archivo: `app/src/main/assets/alimentos_smae.json`
2. Agrega todos los alimentos del PDF en formato JSON
3. Asegúrate de que el JSON sea válido (puedes validarlo en https://jsonlint.com/)

### Paso 3: Usar el Inicializador

#### Opción A: Desde BaseApplication (Recomendado)

```kotlin
class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        
        startKoin {
            androidLogger()
            androidContext(this@BaseApplication)
            modules(homeModule)
        }
        
        // Inicializar base de datos en background
        CoroutineScope(Dispatchers.IO).launch {
            val initializeUseCase = InitializeDatabaseUseCase(
                context = this@BaseApplication,
                repository = get() // Obtener del módulo de Koin
            )
            when (val result = initializeUseCase()) {
                is InitializeResult.Success -> {
                    Log.d("Database", "Base de datos inicializada correctamente")
                }
                is InitializeResult.AlreadyInitialized -> {
                    Log.d("Database", "Base de datos ya estaba inicializada")
                }
                is InitializeResult.Error -> {
                    Log.e("Database", "Error: ${result.message}")
                }
            }
        }
    }
}
```

#### Opción B: Desde SplashViewModel

```kotlin
class SplashViewModel(
    private val initializeDatabaseUseCase: InitializeDatabaseUseCase
) : ViewModel() {
    
    init {
        initializeDatabase()
    }
    
    private fun initializeDatabase() {
        viewModelScope.launch {
            when (val result = initializeDatabaseUseCase()) {
                is InitializeResult.Success -> {
                    // Base de datos inicializada, continuar con la navegación
                    _navigate.value = true
                }
                is InitializeResult.AlreadyInitialized -> {
                    // Ya estaba inicializada, continuar
                    _navigate.value = true
                }
                is InitializeResult.Error -> {
                    // Manejar error
                    Log.e("Splash", "Error inicializando DB: ${result.message}")
                    _navigate.value = true // Continuar de todas formas
                }
            }
        }
    }
}
```

### Paso 4: Agregar al Módulo de Koin

Asegúrate de agregar las dependencias necesarias en tu módulo de Koin:

```kotlin
val homeModule = module {
    // ... otras dependencias ...
    
    // Database
    single { FoodRoomDatBase.getDataBase(context = get()).FoodDao() }
    single { FoodLocalRepository(get()) }
    
    // Use Cases
    single { InitializeDatabaseUseCase(get(), get()) }
    single { GetFoodsByCategoryUseCase(get()) }
    single { GetAllFoodsUseCase(get()) }
    single { SearchFoodsUseCase(get()) }
    // ... otros use cases ...
}
```

## 🔍 Verificar que Funciona

Para verificar que la inicialización funciona correctamente:

```kotlin
// En cualquier ViewModel o UseCase
val getAllFoodsUseCase = GetAllFoodsUseCase(repository)
val foods = getAllFoodsUseCase.invokeSuspend()
Log.d("Database", "Total de alimentos: ${foods.size}")

val getCategoriesUseCase = GetAllCategoriesUseCase(repository)
val categories = getCategoriesUseCase.invokeSuspend()
Log.d("Database", "Categorías: $categories")
```

## 📌 Notas Importantes

1. **Primera ejecución**: La inicialización solo ocurre si la base de datos está vacía
2. **Archivo JSON**: Si no existe `alimentos_smae.json`, se usarán los datos hardcodeados
3. **Rendimiento**: La inicialización se ejecuta en un hilo de background (Dispatchers.IO)
4. **Errores**: Si hay un error, se intenta usar los datos hardcodeados como fallback

## 🛠️ Herramientas Útiles

- **Validar JSON**: https://jsonlint.com/
- **Convertir CSV a JSON**: Si tienes los datos en CSV, puedes usar herramientas online
- **Parser de PDF**: Puedes usar librerías como Apache PDFBox o PyPDF2 para extraer datos automáticamente

## 📚 Categorías del SMAE

Las categorías que debes incluir en el JSON son:

- VERDURAS
- FRUTAS
- CEREALES SIN GRASA
- CEREALES CON GRASA
- ALIMENTOS DE ORIGEN ANIMAL MUY BAJO APORTE DE GRASA
- ALIMENTOS DE ORIGEN ANIMAL BAJO APORTE DE GRASA
- ALIMENTOS DE ORIGEN ANIMAL MODERADO APORTE DE GRASA
- ALIMENTOS DE ORIGEN ANIMAL ALTO APORTE DE GRASA
- LEGUMINOSAS
- LECHE DESCREMADA
- LECHE SEMIDESCREMADA
- LECHE ENTERA
- LECHE CON AZÚCAR
- AZÚCARES SIN GRASA
- AZÚCARES CON GRASA
- ACEITES Y GRASAS
- ACEITE Y GRASAS CON PROTEÍNA

