package com.tealium.remotecommands.facebook

import Commands
import Product
import ProductAvailability
import ProductCondition
import ProductItemParameters
import com.facebook.appevents.AppEventsLogger
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.json.JSONObject
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.util.*

@RunWith(RobolectricTestRunner::class)
class LogProductItemTest {

    @MockK
    lateinit var mockTracker: FacebookAppEventsTrackable

    @InjectMockKs
    var facebookRemoteCommand: FacebookRemoteCommand = FacebookRemoteCommand(null)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun logProductItemNotCalledWithoutProductItemKey() {
        facebookRemoteCommand.parseCommands(arrayOf(Commands.LOG_PRODUCT_ITEM), JSONObject())
        verify {
            mockTracker wasNot Called
        }
        confirmVerified(mockTracker)
    }

    @Test
    fun logProductItemCalledWithAllKeys() {
        val payload = createProductItemJson()

        every {
            mockTracker.logProductItem(
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any()
            )
        } just Runs

        facebookRemoteCommand.parseCommands(arrayOf(Commands.LOG_PRODUCT_ITEM), payload)
        verify {
            mockTracker.logProductItem(
                "product_id",
                AppEventsLogger.ProductAvailability.AVALIABLE_FOR_ORDER,
                AppEventsLogger.ProductCondition.NEW,
                "a toy",
                "http://test.com",
                "http://testlink.com",
                "my toy",
                9.99.toBigDecimal(),
                Currency.getInstance("USD"),
                "gtin",
                "mpn",
                "tealium",
                any()
            )
        }
        confirmVerified(mockTracker)
    }

    @Test
    fun logProductItemNotCalledWithoutProductIdKey() {
        val payload = createProductItemJson()
        payload.optJSONObject(Product.PRODUCT_ITEM).remove(ProductItemParameters.PRODUCT_ID)

        facebookRemoteCommand.parseCommands(arrayOf(Commands.LOG_PRODUCT_ITEM), payload)
        verify {
            mockTracker wasNot Called
        }
        confirmVerified(mockTracker)
    }

    @Test
    fun logProductItemNotCalledWithoutProductDescriptionKey() {
        val payload = createProductItemJson()
        payload.optJSONObject(Product.PRODUCT_ITEM).remove(ProductItemParameters.PRODUCT_DESCRIPTION)

        facebookRemoteCommand.parseCommands(arrayOf(Commands.LOG_PRODUCT_ITEM), payload)
        verify {
            mockTracker wasNot Called
        }
        confirmVerified(mockTracker)
    }

    @Test
    fun logProductItemNotCalledWithoutProductImageLinkKey() {
        val payload = createProductItemJson()
        payload.optJSONObject(Product.PRODUCT_ITEM).remove(ProductItemParameters.PRODUCT_IMAGE_LINK)

        facebookRemoteCommand.parseCommands(arrayOf(Commands.LOG_PRODUCT_ITEM), payload)
        verify {
            mockTracker wasNot Called
        }
        confirmVerified(mockTracker)
    }

    @Test
    fun logProductItemNotCalledWithoutProductLinkKey() {
        val payload = createProductItemJson()
        payload.optJSONObject(Product.PRODUCT_ITEM).remove(ProductItemParameters.PRODUCT_LINK)

        facebookRemoteCommand.parseCommands(arrayOf(Commands.LOG_PRODUCT_ITEM), payload)
        verify {
            mockTracker wasNot Called
        }
        confirmVerified(mockTracker)
    }

    @Test
    fun logProductItemNotCalledWithoutProductTitleKey() {
        val payload = createProductItemJson()
        payload.optJSONObject(Product.PRODUCT_ITEM).remove(ProductItemParameters.PRODUCT_TITLE)

        facebookRemoteCommand.parseCommands(arrayOf(Commands.LOG_PRODUCT_ITEM), payload)
        verify {
            mockTracker wasNot Called
        }
        confirmVerified(mockTracker)
    }

    @Test
    fun logProductItemNotCalledWithoutProductGtinKey() {
        val payload = createProductItemJson()
        payload.optJSONObject(Product.PRODUCT_ITEM).remove(ProductItemParameters.PRODUCT_GTIN)

        facebookRemoteCommand.parseCommands(arrayOf(Commands.LOG_PRODUCT_ITEM), payload)
        verify {
            mockTracker wasNot Called
        }
        confirmVerified(mockTracker)
    }

    @Test
    fun logProductItemNotCalledWithoutProductMpnKey() {
        val payload = createProductItemJson()
        payload.optJSONObject(Product.PRODUCT_ITEM).remove(ProductItemParameters.PRODUCT_MPN)

        facebookRemoteCommand.parseCommands(arrayOf(Commands.LOG_PRODUCT_ITEM), payload)
        verify {
            mockTracker wasNot Called
        }
        confirmVerified(mockTracker)
    }

    @Test
    fun logProductItemNotCalledWithoutProductBrandKey() {
        val payload = createProductItemJson()
        payload.optJSONObject(Product.PRODUCT_ITEM).remove(ProductItemParameters.PRODUCT_BRAND)

        facebookRemoteCommand.parseCommands(arrayOf(Commands.LOG_PRODUCT_ITEM), payload)
        verify {
            mockTracker wasNot Called
        }
        confirmVerified(mockTracker)
    }

    private fun createProductItemJson(): JSONObject {
        val productItemParameters = JSONObject()
        productItemParameters.put(ProductItemParameters.PRODUCT_ID, "product_id")
        productItemParameters.put(
            ProductItemParameters.PRODUCT_AVAILABILITY,
            ProductAvailability.AVAILABLE_FOR_ORDER.value
        )
        productItemParameters.put(ProductItemParameters.PRODUCT_CONDITION, ProductCondition.NEW.value)
        productItemParameters.put(ProductItemParameters.PRODUCT_DESCRIPTION, "a toy")
        productItemParameters.put(ProductItemParameters.PRODUCT_IMAGE_LINK, "http://test.com")
        productItemParameters.put(ProductItemParameters.PRODUCT_LINK, "http://testlink.com")
        productItemParameters.put(ProductItemParameters.PRODUCT_TITLE, "my toy")
        productItemParameters.put(ProductItemParameters.PRODUCT_PRICE_AMOUNT, "9.99")
        productItemParameters.put(ProductItemParameters.PRODUCT_PRICE_CURRENCY, "USD")
        productItemParameters.put(ProductItemParameters.PRODUCT_GTIN, "gtin")
        productItemParameters.put(ProductItemParameters.PRODUCT_MPN, "mpn")
        productItemParameters.put(ProductItemParameters.PRODUCT_BRAND, "tealium")
//        productItemParameters.put(ProductItemParameters.PRODUCT_PARAMETERS, "my toy")

        val payload = JSONObject()
        payload.put(Product.PRODUCT_ITEM, productItemParameters)

        return payload
    }
}