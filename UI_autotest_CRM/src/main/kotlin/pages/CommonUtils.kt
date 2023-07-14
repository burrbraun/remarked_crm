package pages

import Crypt
import com.codeborne.selenide.Selenide
import org.apache.commons.io.comparator.LastModifiedFileComparator
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*
import javax.imageio.ImageIO

class CommonUtils {
    fun matchTwoPictures(pathToEtalonPic: String, actualPic: BufferedImage): Boolean {
        val etalonPic = ImageIO.read(File(pathToEtalonPic))
        if (!((etalonPic.width == actualPic.width) && (etalonPic.height == actualPic.height)))
            return false

        val baosEtalon = ByteArrayOutputStream()
        ImageIO.write(etalonPic, "png", baosEtalon)
        val bytesFromEtalonPic: ByteArray = baosEtalon.toByteArray()

        val baosActual = ByteArrayOutputStream()
        ImageIO.write(actualPic, "png", baosActual)
        val bytesFromActualPic: ByteArray = baosActual.toByteArray()
        for (i in 0 until bytesFromEtalonPic.size - 1) {
            if (bytesFromEtalonPic[i] != bytesFromActualPic[i])
                return false
        }
        return true
    }

    fun smartDownload(rootDir: String): Boolean {
        var file: File? = null
        val timeoutCount = 30
        var i = 0
        while (file == null) {
            try {
                Selenide.sleep(5000)
                i++
                file = findFileInDirectory(rootDir)
            } catch (e: Exception) {
                if (i > 18)
                    return false
            }
        }
        return true
    }

    fun findFileInDirectory(rootDir: String): File {
        val arrayOfFiles = File(rootDir).listFiles()
        if (arrayOfFiles != null) {
            Arrays.sort(arrayOfFiles, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
        }
        if (arrayOfFiles != null) {
            for (i in arrayOfFiles.indices) {
                if (arrayOfFiles[i] != null) {
                    return arrayOfFiles[i]
                }
            }
        }
        return arrayOfFiles[0]
    }

   fun encryptionPasswords(encryptedString: String): String? {

       val secretKey = "CNl2OJ"
       val aesEncryptionDecryption = Crypt()
       //val encryptedString: String? = aesEncryptionDecryption.encrypt(encryptedString, secretKey)
       val decryptedString: String? = aesEncryptionDecryption.decrypt(encryptedString, secretKey)
        return decryptedString
   }
}