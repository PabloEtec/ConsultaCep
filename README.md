# 📍 ConsultaCep - Aplicativo de Consulta de CEP

Aplicativo Android desenvolvido em **Kotlin** que permite consultar informações de endereço através do CEP (Código de Endereçamento Postal) utilizando a API pública do ViaCEP.

## 🎯 Funcionalidades

- ✅ Consulta de endereço por CEP
- ✅ Validação de CEP (8 dígitos)
- ✅ Exibição de informações:
  - Logradouro
  - Bairro
  - Estado (UF)
  - DDD (Código de área telefônico)
  - Localidade (Cidade)

## 🏗️ Arquitetura do Projeto

```
ConsultaCep/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/consultacep/
│   │   │   │   ├── MainActivity.kt           # Tela principal da aplicação
│   │   │   │   ├── api/
│   │   │   │   │   ├── ViaCepClient.kt      # Cliente Retrofit singleton
│   │   │   │   │   └── ViaCepService.kt     # Interface de serviço API
│   │   │   │   └── model/
│   │   │   │       └── ResponseEndereco.kt  # Data class de resposta
│   │   │   └── res/                         # Recursos (layouts, drawables)
│   │   ├── test/                            # Testes unitários
│   │   └── androidTest/                     # Testes instrumentados
│   └── build.gradle.kts                     # Configurações do módulo app
├── build.gradle.kts                         # Configurações raiz
├── settings.gradle.kts                      # Configuração de módulos
└── gradle.properties                        # Propriedades Gradle

```

## 🔧 Tecnologias Utilizadas

- **Linguagem**: Kotlin 100%
- **Plataforma**: Android (SDK 24+, Target SDK 36)
- **Arquitetura**: MVVM com Coroutines
- **Rede**: 
  - Retrofit 2.11.0
  - Gson Converter
- **UI**: 
  - Material Design
  - ConstraintLayout
- **Async**: Coroutines (lifecycleScope)

## 📋 Dependências Principais

```kotlin
// Networking
implementation("com.squareup.retrofit2:retrofit:2.11.0")
implementation("com.squareup.retrofit2:converter-gson:2.11.0")

// Android
implementation(libs.androidx.core.ktx)
implementation(libs.androidx.appcompat)
implementation(libs.material)
implementation(libs.androidx.activity)
implementation(libs.androidx.constraintlayout)
```

## 📝 Descrição dos Componentes

### 1. **MainActivity.kt**
Atividade principal da aplicação que gerencia a interface do usuário.

**Responsabilidades:**
- Captura entrada do usuário (CEP)
- Validação do CEP (exatamente 8 dígitos)
- Realização de chamadas assíncronas à API
- Preenchimento dos campos de resultado

**Fluxo:**
1. Usuário insere CEP no campo `edtCep`
2. Clica no botão "Consultar"
3. Validação do CEP
4. 5 chamadas assíncronas independentes à API ViaCEP
5. Preenchimento dos campos de resultado

### 2. **ViaCepClient.kt**
Cliente Retrofit configurado como **singleton** para consumir a API ViaCEP.

**Configuração:**
- Base URL: `https://viacep.com.br`
- Converter: GsonConverterFactory
- Padrão: Lazy initialization

```kotlin
val instance : ViaCepService by lazy {
    Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ViaCepService::class.java)
}
```

### 3. **ViaCepService.kt**
Interface Retrofit que define os endpoints da API ViaCEP.

**Endpoint:**
- `GET /ws/{cep}/json/` - Busca informações de endereço por CEP

```kotlin
interface ViaCepService {
    @GET("/ws/{cep}/json/")
    suspend fun buscarEndereco(
        @Path("cep") cep : String
    ) : ResponseEndereco
}
```

### 4. **ResponseEndereco.kt**
Data class que mapeia a resposta JSON da API ViaCEP.

```kotlin
data class ResponseEndereco(
    val logradouro : String,   // Rua/Avenida
    val bairro : String,       // Bairro
    val uf : String,           // Estado
    val ddd : String,          // Código de área
    val localidade : String    // Cidade
)
```

## 🚀 Como Usar

### Pré-requisitos
- Android Studio
- JDK 11+
- Gradle 8.0+

### Instalação

1. Clone o repositório:
```bash
git clone https://github.com/PabloEtec/ConsultaCep.git
cd ConsultaCep
```

2. Abra o projeto no Android Studio

3. Sincronize o Gradle:
```bash
./gradlew build
```

4. Compile e execute no emulador ou dispositivo físico

### Uso da Aplicação

1. Inicie o aplicativo
2. Insira um CEP válido (8 dígitos) no campo "CEP"
3. Toque no botão "Consultar"
4. As informações de endereço serão exibidas automaticamente

**Exemplo de CEP válido:** `01310100` (Av. Paulista, São Paulo)

## 🔍 Fluxo da Aplicação

```
┌─────────────────┐
│  Usuário insere │
│     CEP         │
└────────┬────────┘
         │
         ▼
┌─────────────────┐
│   Validação     │◄────── Se CEP ≠ 8 dígitos: Erro!
│   (8 dígitos)   │
└────────┬────────┘
         │ ✓ CEP válido
         ▼
┌──────────────────────┐
│  ViaCepClient.       │
│  buscarEndereco(CEP) │
└────────┬─────────────┘
         │
         ▼
┌──────────────────────┐
│  API ViaCEP retorna  │
│  ResponseEndereco    │
└────────┬─────────────┘
         │
         ▼
┌──────────────────────┐
│  Preencher campos:   │
│  - Logradouro        │
│  - Bairro            │
│  - UF                │
│  - DDD               │
│  - Localidade        │
└──────────────────────┘
```

## 📊 Especificações Técnicas

| Aspecto | Detalhes |
|---------|----------|
| **Min SDK** | 24 (Android 7.0) |
| **Target SDK** | 36 (Android 15) |
| **Compilação** | SDK 36 |
| **Linguagem** | Kotlin 100% |
| **Compatibilidade Java** | 11 |
| **Versão do App** | 1.0 |
| **Código da Versão** | 1 |

## ⚙️ Configuração do Gradle

**Android Configuration:**
```kotlin
namespace = "com.example.consultacep"
minSdk = 24
targetSdk = 36
versionCode = 1
versionName = "1.0"
```

## 🐛 Considerações e Melhorias Futuras

### Problemas Atuais
⚠️ **Múltiplas Chamadas à API:** O código realiza 5 chamadas separadas à API (uma para cada campo). Isso pode ser otimizado.

### Sugestões de Melhoria

1. **Otimizar Chamadas à API**
   ```kotlin
   // Atual: 5 chamadas
   // Melhorado: 1 chamada com resultado reutilizado
   lifecycleScope.launch {
       val endereco = ViaCepClient.instance.buscarEndereco(CEP)
       txtLogradouro.setText(endereco.logradouro)
       txtBairro.setText(endereco.bairro)
       // ... e assim por diante
   }
   ```

2. **Tratamento de Erros**
   - Implementar try-catch
   - Validação de CEP não encontrado
   - Mensagens de erro ao usuário

3. **Loading Indicator**
   - Mostrar progresso durante a consulta
   - Desabilitar botão durante a requisição

4. **Persistência de Dados**
   - Room Database para histórico de consultas
   - Preferências compartilhadas

5. **Testes Unitários**
   - Testes para validação de CEP
   - Mock da API ViaCEP
   - Testes de Coroutines

6. **Formatação de CEP**
   - Máscara de entrada (XXXXX-XXX)
   - Limpeza automática de caracteres

## 📞 Informações da API

**Serviço:** ViaCEP (https://viacep.com.br)

**Endpoint:** `GET /ws/{cep}/json/`

**Exemplo de Resposta:**
```json
{
  "logradouro": "Avenida Paulista",
  "bairro": "Bela Vista",
  "uf": "SP",
  "ddd": "11",
  "localidade": "São Paulo"
}
```

## 📄 Licença

Este projeto está disponível como um projeto educacional.

## 👤 Autor

**PabloEtec**

## 📅 Data de Criação

Setembro de 2026

---

**Desenvolvido com ❤️ em Kotlin**
