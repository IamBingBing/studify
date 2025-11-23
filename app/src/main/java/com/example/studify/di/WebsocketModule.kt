package com.example.studify.di

import com.example.studify.data.repository.ChatRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {
    @Binds
    @Singleton
    abstract fun bindChatMessageHandler(chatRepository: ChatRepository): ChatMessageHandler

    companion object{
        // 1. 사용자 정의 리스너 구현을 제공합니다.
        @Singleton
        @Provides
        fun provideWebSocketListener(messageHandler: ChatMessageHandler): chatWebSocketListener {
            // 2. onMessage에서 처리할 핸들러를 주입받아 사용해야 합니다. (아래 2번 참고)
            return chatWebSocketListener(messageHandler)
        }

        // OkHttpClient도 여기서 제공해야 합니다.
        @Singleton
        @Provides
        fun provideOkHttpClient(): OkHttpClient {
            return OkHttpClient()
        }
    }
}