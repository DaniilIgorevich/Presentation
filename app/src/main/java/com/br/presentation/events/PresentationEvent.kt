package com.br.presentation.events

import android.net.Uri

interface PresentationEvent : Event {
    class SavePresentation(val url: Uri) : PresentationEvent
}