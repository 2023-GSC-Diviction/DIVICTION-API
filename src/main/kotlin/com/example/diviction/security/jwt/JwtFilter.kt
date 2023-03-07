package com.example.diviction.security.jwt

import io.jsonwebtoken.ExpiredJwtException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtFilter(private val tokenProvider: TokenProvider) : OncePerRequestFilter() {
    val logger : Logger = LoggerFactory.getLogger("JWT FILTER")
    companion object{
        const val AUTHORIZATION_HEADER = "Authorization"
        const val BEARER_PREFIX = "Bearer "
    }
    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        val jwt = resolveToken(request)
        logger.info(jwt)

        try {
            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                logger.info("correct token in")
                val authentication: Authentication = tokenProvider.getAuthentication(jwt!!)
                SecurityContextHolder.getContext().authentication = authentication
            }
        }catch(e : ExpiredJwtException)
        {
            logger.info("in filter catch Expired exception")
            response.setHeader("code","AT-DONE")
        }
        filterChain.doFilter(request, response)
    }

    private fun resolveToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader(AUTHORIZATION_HEADER)
        return if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            bearerToken.substring(7)
        } else null
    }
}
