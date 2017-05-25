package com.timzaak.entity

import very.util.security.LongId

case class Comment(id: LongId, fromId: L, toId: L, content: S, time: java.time.LocalDateTime)
