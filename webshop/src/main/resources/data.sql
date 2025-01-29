INSERT INTO products
("id", "created_at", "name", "description", "price", "category", "image_url")
VALUES
    (
        1,
        '2025-01-01 00:00:00',
        'Aria Curve Chair',
        'A simple, yet elegant wooden chair with a natural finish, made from polished oak. The chair features a slightly curved backrest for comfort, four sturdy, tapered legs for stability, and a smooth, contoured seat that enhances both style and functionality. Perfect for dining rooms or minimalist interiors.',
        11900,
        'Dining',
        'images/AriaCurveChair.webp'
    ),
    (
        2,
        '2025-01-01 00:00:00',
        'Oakridge Classic Chair',
        'A modern wooden chair with a unique and artistic design, featuring a natural walnut finish. The chair has a curved ergonomic backrest and a padded seat upholstered in soft, durable fabric for added comfort. Its sleek lines and contemporary aesthetic make it an ideal choice for stylish office areas or accent seating.',
        29900,
        'Office',
        'images/OakridgeClassicChair.webp'
    ),
    (
        3,
        '2025-01-01 00:00:00',
        'Rustic Ladderback Chair',
        'A rustic wooden chair with a weathered, distressed finish, made from reclaimed wood. The chair features a high ladder back design, a sturdy rectangular shape, and a spacious, flat seat for added comfort. Its robust construction makes it perfect for farmhouse-inspired dining rooms or cozy, rustic spaces.',
        12900,
        'Dining',
        'images/RusticLadderbackChair.webp'
    ),
    (
        4,
        '2025-01-01 00:00:00',
        'Velvet Haven Accent Chair',
        'A luxurious accent chair with a plush upholstery and gold-finished leg-tips. Adds a touch of elegance to any living room or bedroom.',
        37900,
        'Bedroom',
        'images/VelvetHavenAccentChair.webp'
    ),
    (
        5,
        '2025-01-01 00:00:00',
        'Lunar Loft Bookshelf',
        'A sleek, modern bookshelf with open shelving units and a powder-coated steel frame. Ideal for organizing books or displaying decorative items.',
        61900,
        'Storage',
        'images/LunarLoftBookshelf.webp'
    ),
    (
        6,
        '2025-01-01 00:00:00',
        'Harbor Breeze King Bed Frame',
        'A coastal-inspired king-size bed frame with a weathered white finish and couchy headboard. Offers both style and comfort.',
        179900,
        'Bedroom',
        'images/HarborBreezeKingBedFrame.webp'
    ),
    (
        7,
        '2025-01-01 00:00:00',
        'Aspen Oak Dining Table',
        'A sturdy dining table made from solid oak, featuring a natural wood grain finish. Perfect for hosting family dinners or gatherings.',
        79900,
        'Dining',
        'images/AspenOakDiningTable.webp'
    ),
    (
        8,
        '2025-01-01 00:00:00',
        'Willow Branch Writing Desk',
        'A compact writing desk crafted from reclaimed wood with built-in drawers for storage. Combines rustic charm with functionality.',
        69900,
        'Office',
        'images/WillowBranchWritingDesk.webp'
    ),
    (
        9,
        '2025-01-01 00:00:00',
        'Urban Nest Coffee Table',
        'A stylish coffee table with a tempered glass top and a twisted wooden base. The perfect centerpiece for a modern living room.',
        89900,
        'Living Room',
        'images/UrbanNestCoffeeTable.webp'
    ),
    (
        10,
        '2025-01-01 00:00:00',
        'Urban Nest Lounge Sofa',
        'A stylish sofa featuring soft, high-density cushions and a rounded wooden frame. The perfect addition for a modern living room.',
        219900,
        'Living Room',
        'images/UrbanNestLoungeSofa.webp'
    )
ON CONFLICT ("id") DO UPDATE
SET
    "created_at" = EXCLUDED."created_at",
    "name" = EXCLUDED."name",
    "description" = EXCLUDED."description",
    "price" = EXCLUDED."price",
    "category" = EXCLUDED."category",
    "image_url" = EXCLUDED."image_url"
;