package com.example.mobileapplumea.data

import com.example.mobileapplumea.R // Make sure this imports your R file for drawables

// Define your Product data class
data class Product(
    val id: String, // Unique identifier for each product
    val name: String,
    val brand: String, // Added brand for more detail
    val price: String,
    val description: String, // Added description
    val imageResId: Int, // Drawable resource ID for the product image
    val rating: Float, // e.g., 4.5f
    val reviewCount: Int, // e.g., 120
    val isBestSeller: Boolean = false, // Add this
    val isHotThisWeek: Boolean = false // Add this
)

// Define your Category data class (THIS IS THE MISSING PART)
data class Category(val name: String, val imageResId: Int)

// List of your actual unique products
val productList = listOf(
    Product(
        id = "prod_001",
        name = "Blossom Kiss Eau de Parfum",
        brand = "Lumea Fragrances",
        price = "$68.00",
        description = "A delicate and romantic pink perfume with notes of cherry blossom, soft musk, and a hint of vanilla, perfect for a captivating everyday scent.",
        imageResId = R.drawable.product_1, // <--- ADD THIS IMAGE
        rating = 4.9f,
        reviewCount = 320
    ),
    Product(
        id = "prod_002",
        name = "Sunset Glow Eyeshadow Palette",
        brand = "Lumea Cosmetics",
        price = "$35.00",
        description = "A versatile eyeshadow palette featuring 12 highly pigmented shades, from warm neutrals to vibrant shimmers, inspired by golden hour hues. Creates endless day-to-night looks.",
        imageResId = R.drawable.product_2, // <--- ADD THIS IMAGE
        rating = 4.7f,
        reviewCount = 410
    ),
    Product(
        id = "prod_003",
        name = "Velvet Matte Lipstick Set",
        brand = "Lumea Pro",
        price = "$48.00",
        description = "A collection of three rich, long-wearing matte lipsticks in essential shades. Delivers intense color with a comfortable, non-drying finish.",
        imageResId = R.drawable.product_3, // <--- ADD THIS IMAGE
        rating = 4.5f,
        reviewCount = 280
    ),
    Product(
        id = "prod_004",
        name = "Rosemary Scalp & Hair Oil",
        brand = "Lumea Organics",
        price = "$22.00",
        description = "An invigorating rosemary-infused oil to promote healthy scalp circulation and strengthen hair from root to tip, reducing breakage and adding natural shine.",
        imageResId = R.drawable.product_4, // <--- ADD THIS IMAGE
        rating = 4.8f,
        reviewCount = 190
    ),
    Product(
        id = "prod_005",
        name = "Flawless Finish Liquid Foundation",
        brand = "Lumea Cosmetics",
        price = "$38.00",
        description = "Achieve a smooth, even complexion with this lightweight yet full-coverage liquid foundation. Blends seamlessly for a natural, radiant finish that lasts all day.",
        imageResId = R.drawable.product_5, // <--- ADD THIS IMAGE
        rating = 4.6f,
        reviewCount = 550
    ),
    Product(
        id = "prod_006",
        name = "Bloom Laundry Fragrance Booster",
        brand = "Lumea Home",
        price = "$15.00",
        description = "Infuse your laundry with the long-lasting, refreshing scent of blooming flowers. A gentle formula that leaves clothes smelling wonderfully fresh.",
        imageResId = R.drawable.product_6, // <--- ADD THIS IMAGE
        rating = 4.4f,
        reviewCount = 120
    ),
    Product(
        id = "prod_007",
        name = "Lash Amplify Mascara",
        brand = "Lumea Pro",
        price = "$24.00",
        description = "Elevate your lashes with intense volume and dramatic length. This smudge-proof formula defines and separates each lash for a stunning, full-fan effect.",
        imageResId = R.drawable.product_7, // <--- ADD THIS IMAGE
        rating = 4.3f,
        reviewCount = 380
    ),
    Product(
        id = "prod_008",
        name = "Soft Flush Powder Blush",
        brand = "Lumea Cosmetics",
        price = "$26.00",
        description = "A silky, finely-milled powder blush that delivers a natural-looking flush of color to your cheeks. Buildable and long-wearing for a healthy, radiant glow.",
        imageResId = R.drawable.product_8, // <--- ADD THIS IMAGE
        rating = 4.7f,
        reviewCount = 210
    )
    // Add more products here as needed
)

// Dummy data for Categories (kept here for now, could be moved to a separate Categories.kt if it grows)
val categories = listOf(
    Category("Skincare", R.drawable.ic_skincare_category),
    Category("Makeup", R.drawable.makeup_category), // You might want distinct images for each category
    Category("Haircare", R.drawable.hair_category),
    Category("Fragrance", R.drawable.fragrance_category),
    Category("Tools", R.drawable.tools_category),
    Category("Body Care", R.drawable.ic_skincare_category)
)

// Dummy for the banner image (if you want a specific image for the banner)
val bannerImageResId = R.drawable.banner_promo_image

val favoriteProductList = listOf(
    Product(
        id = "fav1",
        name = "HydraBoost Serum",
        brand = "Lumea Labs", // Added missing parameter
        price = "$55.00",
        description = "An intense hydrating serum formulated with hyaluronic acid and Vitamin B5 for dewy, plump skin.", // Added missing parameter
        imageResId = R.drawable.product_1, // Using an existing product image for now
        rating = 4.8f, // Added missing parameter
        reviewCount = 250 // Added missing parameter
    ),
    Product(
        id = "fav2",
        name = "Vitamin C Elixir",
        brand = "Lumea Organics", // Added missing parameter
        price = "$48.00",
        description = "Brightening serum with potent Vitamin C to even skin tone and reduce dark spots.", // Added missing parameter
        imageResId = R.drawable.product_2, // Using an existing product image for now
        rating = 4.6f, // Added missing parameter
        reviewCount = 180 // Added missing parameter
    ),
    Product(
        id = "fav3",
        name = "Anti-Aging Cream",
        brand = "Lumea Labs", // Added missing parameter
        price = "$62.00",
        description = "Rich night cream designed to reduce the appearance of wrinkles and improve skin elasticity.", // Added missing parameter
        imageResId = R.drawable.product_3, // Using an existing product image for now
        rating = 4.7f, // Added missing parameter
        reviewCount = 310 // Added missing parameter
    ),
    Product(
        id = "fav4",
        name = "Gentle Cleanser",
        brand = "Lumea Organics", // Added missing parameter
        price = "$25.00",
        description = "A mild, foaming cleanser that effectively removes impurities without stripping natural oils.", // Added missing parameter
        imageResId = R.drawable.product_4, // Using an existing product image for now
        rating = 4.5f, // Added missing parameter
        reviewCount = 95 // Added missing parameter
    ),

)