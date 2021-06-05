package hphuc.project.visafe_version1.vi_safe.app.common

import android.Manifest
import android.security.keystore.KeyProperties
import hphuc.project.visafe_version1.core.app.util.DateTimeFormat

interface AppConstants {
    companion object {
        val PERMISSIONS_IN_APP: Array<String>
            get() = arrayOf( //            Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )

        val LIST_POPULAR_API_DATE: Array<String>
            get() = arrayOf(
                DateTimeFormat.TIMESTAMP_API3.format,
                DateTimeFormat.TIMESTAMP_API6.format,
                DateTimeFormat.YYYY_MM_DD.format,
                DateTimeFormat.DDMMYYYY.format,
                DateTimeFormat.DD_MM_YYYY.format
            )

        val LIST_WORD_EXTENSION: Array<String>
            get() = arrayOf(
                "doc","docm","docx","docm","dotx","dotm"
            )

        val LIST_EXCEL_EXTENSION: Array<String>
            get() = arrayOf(
                "xlsx","xlsm","xltx","xltm","xlam"
            )

        val LIST_POWERPOINT_EXTENSION: Array<String>
            get() = arrayOf(
                "pptx","pptm","potx","potm","ppam","ppsx","ppsm"
            )

        val LIST_MEDIA_SUPPPOT: Array<String>
            get() = arrayOf(
                "mp4", "m4a", "3pg", "flac", "aac", "ts", "gsm", "mp3", "mkv", "wav", "webm", "mid", "xmf", "mxmf", "rtttl", "rtx", "ota", "imy"
            )

        val MONTH_IN_YEAR: Array<String>
            get() = arrayOf(
                "Một","Hai","Ba","Bốn","Năm","Sáu","Bảy","Tám","Chín","Mười","Mười Một","Mười Hai"
            )

        val MONTH_31_DAYS: Array<String>
            get() = arrayOf(
                "Một","Ba","Năm","Bảy","Tám","Mười","Mười Hai"
            )

        val MONTH_30_DAYS: Array<String>
            get() = arrayOf(
                "Bốn","Sáu","Chín","Mười Một"
            )

        val REGEX_PHONE_NUMBER: String
            get() = "\\d{9,10}"

        // Set true to don't check head phone number
        val dontCheckHeadPhoneNumber: Boolean
            get() = false

        val LIST_HEAD_PHONE_NUMBER: Array<String>
            get() = arrayOf(
                "86", "96", "97", "98", "32", "33", "34", "35", "36", "37", "38", "39",
                "81", "82", "83", "84", "85", "88", "91", "94",
                "89", "90", "93", "70", "79", "77", "76", "78",
                "92", "56", "58", "99", "59"
            )

        val FORCE_LOGIN_USER_NOT_SUPPORTED: Int
            get() = 1
        val FORCE_LOGIN_TOKEN: Int
            get() = 2
        val FORCE_LOGIN_DEFAULT: Int
            get() = 0

        val REGEX_LIST_INT_SEPARATED_COMMA: String
            get() = "(\\d+)(,\\s*\\d+)*"
        val REGEX_FLOAT_NUMBER: String
            get() = "([0-9]+[.])?[0-9]{1}"

        val RABBIT_HOST: String
            get() = "222.255.238.29"
        val RABBIT_HOST_UAT: String
            get() = "222.255.238.29"
        val RABBIT_PORT: Int
            get() = 3005
        val RABBIT_VHOST_CHATTING: String
            get() = "chatting-uniprime"
        val RABBIT_USER_CHATTING: String
            get() = "uniprime"
        val RABBIT_PASSWORD_CHATTING: String
            get() = "uniprim0mnv"
        val RABBIT_EXCHANGE_SEND:String
            get() ="amq.topic"
        val RABBIT_EXCHANGE_DIRECT:String
            get() ="amq.direct"

        val MAP_STYLE: String
            get() = "https://images.vietbando.com/Style/vt_vbddefault/bbb9c9ad-00b9-4066-bc88-06b401b8eddd"

        val PHOTOS_MINE_TYPE: String
            get() = "image/jpeg"
        val VIDEO_MINE_TYPE: String
            get() = "video/mp4"

        val MAXIMUM_IMAGE_SIZE_CHAT = 25 * 1024 * 1024 // change to bytes max 25 mb
        val MAXIMUM_VIDEO_SIZE_CHAT = 100 * 1024 * 1024 // change to bytes max 100 mb

        val REQUEST_CODE_GET_ATTACH_FILE: Int
            get() = 9
        val REQUEST_CODE_PROJECT_DETAIL_FILTER: Int
            get() = 10
        val REQUEST_CODE_CHAT_PROFILE: Int
            get() = 27
        val REQUEST_CODE_CHAT_LIST_USER: Int
            get() = 28
        val REQUEST_CODE_CHAT_UPDATE_AVATAR_GROUP: Int
            get() = 30
        val REQUEST_CODE_CHAT_LIST_IMAGES: Int
            get() = 29
        val REQUEST_CODE_SEARCH_CHAT: Int
            get() = 40

        var IS_VIDEO_CALL = false
        var VIDEO_CALL_ROOM_ID : Long = 0
        var VIDEO_CALL_ROOM_NAME : String = ""
        var VIDEO_CALL_ROOM_AVATAR : String = ""

        const val ANDROID_KEYSTORE = "AndroidKeyStore"
        const val ENCRYPTION_BLOCK_MODE = KeyProperties.BLOCK_MODE_GCM
        const val ENCRYPTION_PADDING = KeyProperties.ENCRYPTION_PADDING_NONE
        const val ENCRYPTION_ALGORITHM = KeyProperties.KEY_ALGORITHM_AES
        const val secretKeyName = "secretKeyName"
        const val KEY_SIZE = 256
        const val CANCEL_FINGERPRINT = 13
        const val CANCEL_FINGERPRINT_WITH_TOUCH_OUTSIZE = 10

        // role
        const val POLICY = "POLICY"
        const val CIVIL_DEFENSE = "CIVIL_DEFENSE"
        const val STREET_BODYGUARD = "STREET_BODYGUARD"
        const val HOSPITAL = "HOSPITAL"
        const val FIRE_FIGHT = "FIRE_FIGHT"

        val REQUEST_CODE_PICK_CONTRACT: Int
            get() = 9
        val LIST_ALPHABET = mutableListOf(
            "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R",
            "S", "T", "U", "V", "W", "X", "Y", "Z", "#"
        )
    }
}