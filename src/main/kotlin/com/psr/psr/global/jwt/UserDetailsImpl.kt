package com.psr.psr.global.jwt

import com.psr.psr.user.entity.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserDetailsImpl(val user:User) :UserDetails {

    var enabled: Boolean = true
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        // todo: user role entity 생기면 넣어두기
        val grantedAuthority = SimpleGrantedAuthority("user.role.name")
        return mutableListOf(grantedAuthority)
    }

    override fun getPassword(): String = user.password

    override fun getUsername(): String = user.id.toString()

    override fun isAccountNonExpired(): Boolean = enabled

    override fun isAccountNonLocked(): Boolean = enabled

    override fun isCredentialsNonExpired(): Boolean = enabled

    override fun isEnabled(): Boolean = enabled
}