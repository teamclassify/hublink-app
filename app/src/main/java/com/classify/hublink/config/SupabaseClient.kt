package com.classify.hublink.config

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.storage.Storage
import com.classify.hublink.BuildConfig

val supabase = createSupabaseClient(
    supabaseUrl = BuildConfig.SUPABASE_URL,
    supabaseKey = BuildConfig.SUPABASE_KEY
) {
    install(Storage)
}