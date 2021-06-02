package hphuc.project.visafe_version1.vi_safe.app.network

import hphuc.project.visafe_version1.vi_safe.app.common.AppConstants

class ConfigURL {
    companion object {
        private const val PORT_UAT = 8001
        private const val PORT_PRODUCT = 8003
        private const val API_SMART_HOME_PRODUCT = "http://uniprimeapi.minerva.vn/api/"
        //        private const val API_uniprime_UAT = "http://sgc.minerva.vn:$PORT_UAT/api/"
        private const val API_SMART_HOME_UAT = "http://uniprimeapidev.minerva.vn/api/"
        private const val API_SMART_HOME_DEV="http://uniprimeapidev.minerva.vn:7110/api/ "
        const val MAP_STYLE="https://images.minerva.vn/"

        @JvmStatic
        fun getApiUrl(): String {
            return when {
                else -> {
                    API_SMART_HOME_UAT
                }
            }
        }

        @JvmStatic
        fun getApiPort(): Int {
            return when{
                else -> {
                    PORT_UAT
                }
            }
        }

        @JvmStatic
        fun getApiRabbitHost(): String {
            return when {
                else -> {
                    AppConstants.RABBIT_HOST_UAT
                }
            }
        }
    }
}