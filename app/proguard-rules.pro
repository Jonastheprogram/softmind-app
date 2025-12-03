# Mantenha (não ofusque) todas as classes no seu pacote 'model'
# Isso é crucial para Retrofit/Gson funcionar
# Lembre-se de usar o SEU nome de pacote
-keep class br.com.fiap.softmind.model.** { *; }

# Regras adicionais que ajudam bibliotecas como Gson
-keepattributes Signature
-keepattributes *Annotation*
-keep class kotlin.** { *; }
-keep class kotlin.collections.** { *; }
