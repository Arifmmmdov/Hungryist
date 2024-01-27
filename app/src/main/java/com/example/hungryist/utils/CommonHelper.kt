package com.example.hungryist.utils

import android.util.Patterns
import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.Phonenumber


class CommonHelper {

    data class PhoneValidity (
        var phone: String? = null,
        var code: String? = null,
        var isValid: Boolean = false
    )

    companion object{
        fun isValidEmail(email: String?): Boolean? {
            return email?.let { Patterns.EMAIL_ADDRESS.matcher(it).matches() }
        }
        fun isValidPhoneNumber(mobNumber: String?, countryCode: String): PhoneValidity {
            val phoneNumberValidate = PhoneValidity()
            val phoneNumberUtil: PhoneNumberUtil = PhoneNumberUtil.getInstance()
            val phoneNumber: Phonenumber.PhoneNumber
            var isMobile: PhoneNumberUtil.PhoneNumberType? = null
            var isValid = false
            try {
                val isoCode: String = phoneNumberUtil.getRegionCodeForCountryCode(countryCode.toInt())
                phoneNumber = phoneNumberUtil.parse(mobNumber, isoCode)
                isValid = phoneNumberUtil.isValidNumber(phoneNumber)
                isMobile = phoneNumberUtil.getNumberType(phoneNumber)
                phoneNumberValidate.code = java.lang.String.valueOf(phoneNumber.countryCode)
                phoneNumberValidate.phone = java.lang.String.valueOf(phoneNumber.nationalNumber)
                phoneNumberValidate.isValid = false
            } catch (e: NumberParseException) {
                e.printStackTrace()
            } catch (e: NullPointerException) {
                e.printStackTrace()
            } catch (e: NumberFormatException) {
                e.printStackTrace()
            }
            if (isValid && (PhoneNumberUtil.PhoneNumberType.MOBILE === isMobile ||
                        PhoneNumberUtil.PhoneNumberType.FIXED_LINE_OR_MOBILE === isMobile)
            ) {
                phoneNumberValidate.isValid = true
            }
            return phoneNumberValidate
        }

        fun isValidPhoneNumber(phoneNumber: String): Boolean {
            val regex = """(\+994\s?)?([5-9][0-9]{1,2})\s?([0-9]{2})\s?([0-9]{2})\s?([0-9]{2})""".toRegex()
            return regex.matches(phoneNumber)
        }
    }
}