INSERT INTO product
("id", "created_on", "name", "description", "price", "category", "image_url")
VALUES
    (
        1001,
        '2025-01-01 00:00:00',
        'Aria Curve Chair',
        'A simple, yet elegant wooden chair with a natural finish, made from polished oak. The chair features a slightly curved backrest for comfort, four sturdy, tapered legs for stability, and a smooth, contoured seat that enhances both style and functionality. Perfect for dining rooms or minimalist interiors.',
        11900,
        'Dining',
        'AriaCurveChair.webp'
    ),
    (
        1002,
        '2025-01-01 00:00:00',
        'Oakridge Classic Chair',
        'A modern wooden chair with a unique and artistic design, featuring a natural walnut finish. The chair has a curved ergonomic backrest and a padded seat upholstered in soft, durable fabric for added comfort. Its sleek lines and contemporary aesthetic make it an ideal choice for stylish office areas or accent seating.',
        29900,
        'Office',
        'OakridgeClassicChair.webp'
    ),
    (
        1003,
        '2025-01-01 00:00:00',
        'Rustic Ladderback Chair',
        'A rustic wooden chair with a weathered, distressed finish, made from reclaimed wood. The chair features a high ladder back design, a sturdy rectangular shape, and a spacious, flat seat for added comfort. Its robust construction makes it perfect for farmhouse-inspired dining rooms or cozy, rustic spaces.',
        12900,
        'Dining',
        'RusticLadderbackChair.webp'
    ),
    (
        1004,
        '2025-01-01 00:00:00',
        'Velvet Haven Accent Chair',
        'A luxurious accent chair with a plush upholstery and gold-finished leg-tips. Adds a touch of elegance to any living room or bedroom.',
        37900,
        'Bedroom',
        'VelvetHavenAccentChair.webp'
    ),
    (
        1005,
        '2025-01-01 00:00:00',
        'Lunar Loft Bookshelf',
        'A sleek, modern bookshelf with open shelving units and a powder-coated steel frame. Ideal for organizing books or displaying decorative items.',
        61900,
        'Storage',
        'LunarLoftBookshelf.webp'
    ),
    (
        1006,
        '2025-01-01 00:00:00',
        'Harbor Breeze King Bed Frame',
        'A coastal-inspired king-size bed frame with a weathered white finish and couchy headboard. Offers both style and comfort.',
        179900,
        'Bedroom',
        'HarborBreezeKingBedFrame.webp'
    ),
    (
        1007,
        '2025-01-01 00:00:00',
        'Aspen Oak Dining Table',
        'A sturdy dining table made from solid oak, featuring a natural wood grain finish. Perfect for hosting family dinners or gatherings.',
        79900,
        'Dining',
        'AspenOakDiningTable.webp'
    ),
    (
        1008,
        '2025-01-01 00:00:00',
        'Willow Branch Writing Desk',
        'A compact writing desk crafted from reclaimed wood with built-in drawers for storage. Combines rustic charm with functionality.',
        69900,
        'Office',
        'WillowBranchWritingDesk.webp'
    ),
    (
        1009,
        '2025-01-01 00:00:00',
        'Urban Nest Coffee Table',
        'A stylish coffee table with a tempered glass top and a twisted wooden base. The perfect centerpiece for a modern living room.',
        89900,
        'Living Room',
        'UrbanNestCoffeeTable.webp'
    ),
    (
        1010,
        '2025-01-01 00:00:00',
        'Urban Nest Lounge Sofa',
        'A stylish sofa featuring soft, high-density cushions and a rounded wooden frame. The perfect addition for a modern living room.',
        219900,
        'Living Room',
        'UrbanNestLoungeSofa.webp'
    )
ON CONFLICT ("id") DO UPDATE
SET
    "created_on" = EXCLUDED."created_on",
    "name" = EXCLUDED."name",
    "description" = EXCLUDED."description",
    "price" = EXCLUDED."price",
    "category" = EXCLUDED."category",
    "image_url" = EXCLUDED."image_url"
;