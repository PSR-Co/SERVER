package com.psr.psr.global.entityListener

import org.springframework.beans.BeansException
import org.springframework.context.ApplicationContext

import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component


@Component
class BeanUtils : ApplicationContextAware {
    @Throws(BeansException::class)
    override fun setApplicationContext(act: ApplicationContext) {
        applicationContext = act
    }

    companion object {
        private var applicationContext: ApplicationContext? = null
        fun <T> getBean(tClass: Class<T>): T {
            return applicationContext!!.getBean(tClass)
        }
    }

}