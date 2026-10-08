package com.anujamart.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductCatalogSeed {

    public static final Object[][] CATALOG = new Object[][]{
        // 1. Electronics (20 Products)
        {"Wireless Noise-Canceling Headphones", "Electronics", 2999.00, 45, "Over-ear Bluetooth headphones with active noise cancellation and 30-hour battery life.", "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=500"},
        {"Smart Fitness Watch Ultra", "Electronics", 2499.00, 60, "Waterproof smartwatch with SpO2 monitor, heart rate tracking, and 1.8-inch AMOLED display.", "https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=500"},
        {"True Wireless Gaming Earbuds", "Electronics", 1799.00, 75, "Low latency wireless earbuds with dual mic noise reduction and fast charging case.", "https://images.unsplash.com/photo-1590658268037-6bf12165a8df?w=500"},
        {"Portable Bluetooth Speaker 20W", "Electronics", 1499.00, 50, "Rugged waterproof outdoor speaker with deep bass radiator and 12-hour playtime.", "https://images.unsplash.com/photo-1608043152269-423dbba4e7e1?w=500"},
        {"Mechanical Gaming Keyboard RGB", "Electronics", 3499.00, 40, "Tactile blue switch mechanical keyboard with per-key customizable RGB backlighting.", "https://images.unsplash.com/photo-1587829741301-dc798b83add3?w=500"},
        {"Ergonomic Wireless Mouse", "Electronics", 899.00, 85, "2.4GHz rechargeable ergonomic vertical mouse with silent clicks and adjustable DPI.", "https://images.unsplash.com/photo-1615663245857-ac93bb7c39e7?w=500"},
        {"1080p Full HD USB Webcam", "Electronics", 1299.00, 55, "Plug-and-play streaming webcam with built-in dual stereo microphones and privacy shutter.", "https://images.unsplash.com/photo-1588508065123-287b28e013da?w=500"},
        {"Ultra-Slim 20000mAh Power Bank", "Electronics", 1699.00, 90, "22.5W Fast-charging power bank with dual USB output and Type-C Power Delivery.", "https://images.unsplash.com/photo-1609592426861-15c00e620579?w=500"},
        {"Smart WiFi LED Desk Lamp", "Electronics", 1199.00, 65, "Touch-controlled dimmable desk lamp with app control, timer, and USB charging port.", "https://images.unsplash.com/photo-1534073828943-f801091bb18c?w=500"},
        {"Foldable Laptop Stand Aluminum", "Electronics", 799.00, 110, "Ventilated ergonomic height-adjustable laptop riser compatible with all 10-16 inch laptops.", "https://images.unsplash.com/photo-1527864550417-7fd91fc51a46?w=500"},
        {"4K 60Hz HDMI Capture Card", "Electronics", 2199.00, 35, "Zero-lag video capture card for game streaming, online teaching, and video conferencing.", "https://images.unsplash.com/photo-1550745165-9bc0b252726f?w=500"},
        {"Dual-Band AC1200 WiFi Router", "Electronics", 1899.00, 40, "High-speed wireless internet router with 4 high-gain antennas and parental controls.", "https://images.unsplash.com/photo-1544197150-b99a580bb7a8?w=500"},
        {"USB-C 7-in-1 Multiport Hub", "Electronics", 1599.00, 70, "Aluminum Type-C adapter with 4K HDMI, 100W PD, SD card reader, and 3 USB 3.0 ports.", "https://images.unsplash.com/photo-1586953208448-b95a79798f07?w=500"},
        {"Dynamic Studio Recording Microphone", "Electronics", 3299.00, 30, "Cardioid condenser USB microphone with shock mount and pop filter for podcasting.", "https://images.unsplash.com/photo-1590602847861-f357a9332bbc?w=500"},
        {"Wireless Fast Charging Pad 15W", "Electronics", 699.00, 95, "Qi-certified universal fast wireless charger with LED indicator and temperature protection.", "https://images.unsplash.com/photo-1622445262464-84b1456045b6?w=500"},
        {"Portable Mini Projector HD", "Electronics", 5999.00, 20, "Home cinema video projector supporting 1080p resolution with built-in HiFi speaker.", "https://images.unsplash.com/photo-1517604931442-7e0c8ed2963c?w=500"},
        {"Bluetooth FM Transmitter Car Adapter", "Electronics", 599.00, 120, "Wireless car audio receiver with QC 3.0 fast charging and hands-free calling mic.", "https://images.unsplash.com/photo-1546776310-eef45dd6d63c?w=500"},
        {"RGB Gaming Mouse Pad XXL", "Electronics", 749.00, 80, "Large extended cloth desk mat with 14 customizable LED lighting modes and anti-slip rubber.", "https://images.unsplash.com/photo-1616763355548-1b606f439f86?w=500"},
        {"Smart Universal Remote Hub", "Electronics", 1099.00, 50, "WiFi IR smart controller compatible with Alexa & Google Assistant for AC, TV, and Setup box.", "https://images.unsplash.com/photo-1558002038-1055907df827?w=500"},
        {"3-Axis Handheld Smartphone Gimbal", "Electronics", 4999.00, 25, "Motorized video stabilizer with face tracking, time-lapse, and zoom control wheel.", "https://images.unsplash.com/photo-1583394838336-acd977736f90?w=500"},

        // 2. Fashion and Clothing (20 Products)
        {"Men's Premium Cotton Slim Shirt", "Fashion and Clothing", 1299.00, 50, "Breathable 100% organic cotton slim-fit casual shirt suitable for formal and casual wear.", "https://images.unsplash.com/photo-1596755094514-f87e34085b2c?w=500"},
        {"Women's Floral Summer Maxi Dress", "Fashion and Clothing", 1899.00, 35, "Lightweight, elegant floral print maxi dress crafted from soft, flowing rayon fabric.", "https://images.unsplash.com/photo-1572804013309-59a88b7e92f1?w=500"},
        {"Men's Classic Denim Jacket", "Fashion and Clothing", 2499.00, 40, "Vintage washed heavy denim trucker jacket with button closure and chest pockets.", "https://images.unsplash.com/photo-1576995853123-5a10305d93c0?w=500"},
        {"Women's High-Waist Skinny Jeans", "Fashion and Clothing", 1599.00, 60, "Stretchable ankle-length denim jeans with contour waistband and classic 5-pocket styling.", "https://images.unsplash.com/photo-1541099649105-f69ad21f3246?w=500"},
        {"Unisex Oversized Graphic Hoodie", "Fashion and Clothing", 1799.00, 55, "Cozy heavyweight fleece hoodie with aesthetic street style front print and kangaroo pocket.", "https://images.unsplash.com/photo-1556905055-8f358a7a47b2?w=500"},
        {"Men's Stretch Chino Trousers", "Fashion and Clothing", 1399.00, 65, "Tapered fit cotton chinos engineered with elastane stretch for all-day office comfort.", "https://images.unsplash.com/photo-1624378439575-d8705ad7ae80?w=500"},
        {"Women's Knit Cardigan Sweater", "Fashion and Clothing", 1499.00, 45, "Soft open-front knitted long cardigan with ribbed cuffs and relaxed slouchy silhouette.", "https://images.unsplash.com/photo-1434389677669-e08b4cac3105?w=500"},
        {"Men's Crewneck Solid T-Shirt (Pack of 3)", "Fashion and Clothing", 999.00, 90, "Essential combed bio-washed cotton tees in black, white, and navy for daily layering.", "https://images.unsplash.com/photo-1521572267360-ee0c2909d518?w=500"},
        {"Women's Bohemian Tiered Skirt", "Fashion and Clothing", 1199.00, 40, "Elastic high-waist A-line flared midi skirt made with breathable printed cotton.", "https://images.unsplash.com/photo-1583496661160-fb5886a0aaaa?w=500"},
        {"Men's Linen Casual Beach Shirt", "Fashion and Clothing", 1499.00, 50, "Mandarin collar pure linen half-sleeve shirt keeping you cool on hot sunny days.", "https://images.unsplash.com/photo-1602810318383-e386cc2a3ccf?w=500"},
        {"Women's Tailored Office Blazer", "Fashion and Clothing", 2799.00, 30, "Single-breasted formal business blazer with structured shoulder pads and flap pockets.", "https://images.unsplash.com/photo-1548624149-f9b1859aa7d0?w=500"},
        {"Unisex Running Athletic Shorts", "Fashion and Clothing", 699.00, 85, "Quick-dry moisture-wicking active shorts with zippered pockets and adjustable drawcord.", "https://images.unsplash.com/photo-1539109136881-3be0616acf4b?w=500"},
        {"Women's Yoga Leggings High-Waist", "Fashion and Clothing", 999.00, 75, "Non-see-through 4-way stretch squat-proof workout tights with deep phone side pockets.", "https://images.unsplash.com/photo-1506630448388-4e683c67ddb0?w=500"},
        {"Men's Thermal Winter Puffer Vest", "Fashion and Clothing", 1999.00, 40, "Windproof insulated sleeveless puffer jacket with fleece lining and zipper closure.", "https://images.unsplash.com/photo-1544441893-675973e31985?w=500"},
        {"Women's Silk Satin Nightwear Set", "Fashion and Clothing", 1399.00, 50, "Luxurious 2-piece satin pajama set featuring a button-down shirt and matching shorts.", "https://images.unsplash.com/photo-1515886657613-9f3515b0c78f?w=500"},
        {"Men's Athleisure Track Pants", "Fashion and Clothing", 1099.00, 70, "Slim tapered training joggers with ribbed ankle cuffs and elasticated waist band.", "https://images.unsplash.com/photo-1552902865-b72c031ac5ea?w=500"},
        {"Women's Cotton Embroidered Kurti", "Fashion and Clothing", 1299.00, 60, "Traditional handcrafted ethnic straight kurti with delicate thread work embroidery.", "https://images.unsplash.com/photo-1610030469983-98e550d6193c?w=500"},
        {"Men's Formal Oxford Dress Shirt", "Fashion and Clothing", 1499.00, 45, "Wrinkle-resistant pinpoint oxford cotton shirt with spread collar and French cuffs.", "https://images.unsplash.com/photo-1603252109303-2751441dd157?w=500"},
        {"Women's Casual Denim Dungarees", "Fashion and Clothing", 1799.00, 35, "Vintage adjustable strap denim overalls featuring utility chest and side pockets.", "https://images.unsplash.com/photo-1578587018452-892bacefd3f2?w=500"},
        {"Unisex Wool Blend Winter Beanie", "Fashion and Clothing", 499.00, 110, "Chunky cable-knit warm winter skull cap crafted from soft anti-itch acrylic yarn.", "https://images.unsplash.com/photo-1576871337622-98d48d1cf531?w=500"},

        // 3. Beauty and Personal Care (15 Products)
        {"Organic Aloe Vera Face Cream", "Beauty and Personal Care", 499.00, 80, "Daily hydrating face cream with natural aloe vera extract, shea butter, and Vitamin E.", "https://images.unsplash.com/photo-1556228720-195a672e8a03?w=500"},
        {"Vitamin C Brightening Facial Serum (30ml)", "Beauty and Personal Care", 799.00, 70, "20% Vitamin C serum with Hyaluronic Acid for reducing dark spots and glowing skin.", "https://images.unsplash.com/photo-1620916566398-39f1143ab7be?w=500"},
        {"Tea Tree Purifying Foaming Face Wash", "Beauty and Personal Care", 399.00, 95, "Gentle acne-control cleanser with pure Australian tea tree oil and salicylic acid.", "https://images.unsplash.com/photo-1556228852-80b6e5eeff06?w=500"},
        {"Argan Oil Hair Repair Mask (200g)", "Beauty and Personal Care", 649.00, 60, "Deep conditioning Moroccan argan oil mask that restores frizz-free shine and strength.", "https://images.unsplash.com/photo-1522337360788-8b13dee7a37e?w=500"},
        {"Mineral Matte Sunscreen SPF 50 PA+++", "Beauty and Personal Care", 599.00, 85, "Non-greasy, zero white-cast broad spectrum sun protection lotion with zinc oxide.", "https://images.unsplash.com/photo-1598440947619-2c35fc9aa908?w=500"},
        {"Rosewater Facial Mist & Hydrating Toner", "Beauty and Personal Care", 349.00, 100, "100% pure steam-distilled rose water that tightens pores and revitalizes dull skin.", "https://images.unsplash.com/photo-1608248597359-3b9943dc7939?w=500"},
        {"Coconut & Shea Body Butter (250g)", "Beauty and Personal Care", 549.00, 75, "Ultra-rich moisturizing body cream providing 48-hour intense hydration for dry skin.", "https://images.unsplash.com/photo-1571781926291-c477ebfd024b?w=500"},
        {"Sonic Electric Toothbrush Rechargeable", "Beauty and Personal Care", 1499.00, 45, "40,000 VPM sonic toothbrush with 5 smart cleaning modes and 2-minute timer.", "https://images.unsplash.com/photo-1559591937-e10c7a52e1c9?w=500"},
        {"Professional 2000W Ionic Hair Dryer", "Beauty and Personal Care", 1899.00, 35, "Salon-grade blow dryer with negative ion technology and diffuser attachment.", "https://images.unsplash.com/photo-1522337094344-6644f6f43702?w=500"},
        {"Charcoal Peel-Off Blackhead Mask", "Beauty and Personal Care", 399.00, 90, "Activated bamboo charcoal detox mask that deeply cleanses clogged pores and dirt.", "https://images.unsplash.com/photo-1567928815117-6479fdfa1f86?w=500"},
        {"Lavender & Chamomile Essential Oil Set", "Beauty and Personal Care", 699.00, 65, "Pure therapeutic-grade aromatherapy essential oils for relaxation, sleep, and diffusers.", "https://images.unsplash.com/photo-1608571423902-eed4a5ad8108?w=500"},
        {"Natural Beard Grooming Oil & Balm Kit", "Beauty and Personal Care", 599.00, 70, "Cedarwood scented beard care kit enriched with jojoba oil for soft, nourished beard.", "https://images.unsplash.com/photo-1621607512214-68297480165e?w=500"},
        {"Ceramic Hair Straightener Brush", "Beauty and Personal Care", 1299.00, 40, "Fast heating anti-scald electric hair straightening comb with LED temperature display.", "https://images.unsplash.com/photo-1560066984-138dadb4c035?w=500"},
        {"Exfoliating Walnut Face Scrub (150g)", "Beauty and Personal Care", 349.00, 85, "Natural micro-walnut shell granule scrub that gently buffs away dead skin cells.", "https://images.unsplash.com/photo-1535585209827-a15fcdbc4c2d?w=500"},
        {"Under-Eye Gel with Green Tea & Caffeine", "Beauty and Personal Care", 449.00, 80, "Cooling under-eye treatment roll-on that depuffs tired eyes and lightens dark circles.", "https://images.unsplash.com/photo-1512290900672-1f41bf52a7c5?w=500"},

        // 4. Home and Kitchen (15 Products)
        {"Stainless Steel Thermal Bottle (1L)", "Home and Kitchen", 799.00, 60, "Double-wall vacuum insulated water bottle keeping beverages cold for 24h or hot for 12h.", "https://images.unsplash.com/photo-1602143407151-7111542de6e8?w=500"},
        {"Cast Iron Pre-Seasoned Skillet (10-Inch)", "Home and Kitchen", 1499.00, 40, "Heavy-duty cast iron frying pan for searing, baking, braising, and frying.", "https://images.unsplash.com/photo-1590794056226-79ef3a8147e1?w=500"},
        {"Automatic Cordless Electric Kettle 1.8L", "Home and Kitchen", 999.00, 65, "1500W rapid boiling kettle with stainless steel body and auto shut-off protection.", "https://images.unsplash.com/photo-1588681664899-f142ff2dc9b1?w=500"},
        {"French Press Coffee Maker 600ml", "Home and Kitchen", 899.00, 50, "Heat-resistant borosilicate glass coffee and tea plunger with 4-level filtration system.", "https://images.unsplash.com/photo-1544787219-7f47ccb76574?w=500"},
        {"Non-Stick 3-Piece Cookware Set", "Home and Kitchen", 2199.00, 35, "Granite-coated induction-friendly frying pan, kadai with lid, and flat dosa tawa.", "https://images.unsplash.com/photo-1584990347449-a2926715f3e9?w=500"},
        {"Microfiber Bed Sheet Set King Size", "Home and Kitchen", 1199.00, 55, "Ultra-soft wrinkle-free breathable double bedsheet with 2 matching pillow covers.", "https://images.unsplash.com/photo-1522771739844-6a9f6d5f14af?w=500"},
        {"Aromatherapy Ultrasonic Humidifier 500ml", "Home and Kitchen", 1399.00, 45, "Quiet cool mist aroma diffuser with 7-color LED lights and remote control.", "https://images.unsplash.com/photo-1608571423902-eed4a5ad8108?w=500"},
        {"Chef's Kitchen Knife Set with Wooden Block", "Home and Kitchen", 1799.00, 30, "High-carbon stainless steel knife set including chef knife, santoku, and utility knives.", "https://images.unsplash.com/photo-1593618998160-e34014e67546?w=500"},
        {"Digital Kitchen Food Weighing Scale", "Home and Kitchen", 599.00, 80, "Precision 10kg digital scale with tare function and backlit LCD display for baking.", "https://images.unsplash.com/photo-1594913785162-e678a0c23ddb?w=500"},
        {"Ceramic Dinner Plates Set (Pack of 6)", "Home and Kitchen", 1699.00, 25, "Handcrafted microwave-safe stoneware dinner plates with elegant matte glaze finish.", "https://images.unsplash.com/photo-1614735241165-6756e1df61ab?w=500"},
        {"Automatic Robot Vacuum Cleaner", "Home and Kitchen", 9999.00, 15, "Smart robotic vacuum with anti-collision sensors, auto-charging, and strong suction.", "https://images.unsplash.com/photo-1589782182703-2aaa69037b5b?w=500"},
        {"Glass Food Storage Containers (Set of 4)", "Home and Kitchen", 1099.00, 70, "Leakproof airtight borosilicate glass meal prep containers with locking lids.", "https://images.unsplash.com/photo-1590490360182-c33d57733427?w=500"},
        {"Natural Bamboo Cutting Board Set", "Home and Kitchen", 699.00, 85, "Organic thick chopping boards with juice grooves for slicing meat, vegetables, and bread.", "https://images.unsplash.com/photo-1594834749740-74b3f6764be4?w=500"},
        {"Memory Foam Ergonomic Sleeping Pillow", "Home and Kitchen", 1299.00, 40, "Orthopedic contour neck support pillow with breathable washable cover.", "https://images.unsplash.com/photo-1584100936595-c0654b55a2e2?w=500"},
        {"Indoor Ceramic Planters with Drainage (Set of 3)", "Home and Kitchen", 849.00, 60, "Modern succulent and houseplant pots with matching bamboo saucers.", "https://images.unsplash.com/photo-1485955900006-10f4d324d411?w=500"},

        // 5. Books and Stationery (15 Products)
        {"Leather Bound Refillable Daily Journal", "Books and Stationery", 699.00, 90, "Handmade vintage genuine leather notebook with 200 pages of thick unlined paper.", "https://images.unsplash.com/photo-1544716278-ca5e3f4abd8c?w=500"},
        {"Hardcover Productivity Planner & Organizer", "Books and Stationery", 799.00, 75, "Undated goal-setting daily journal with habit trackers, weekly reviews, and ribbon bookmark.", "https://images.unsplash.com/photo-1506784983877-45594efa4cbe?w=500"},
        {"Executive Fountain Pen with Ink Converter", "Books and Stationery", 999.00, 50, "Smooth fine-nib brass fountain pen in a luxury presentation gift box.", "https://images.unsplash.com/photo-1583485088034-697b5bc54ccd?w=500"},
        {"Dual-Tip Brush Marker Pens (Set of 24)", "Books and Stationery", 849.00, 65, "Flexible brush and fine tips for calligraphy, bullet journaling, sketching, and coloring.", "https://images.unsplash.com/photo-1513542789411-b6a5d4f31634?w=500"},
        {"A5 Dotted Grid Bullet Journal Notebook", "Books and Stationery", 499.00, 110, "120 GSM bleed-resistant ivory paper with durable faux-leather hardcover and inner pocket.", "https://images.unsplash.com/photo-1586075010923-2dd4570fb338?w=500"},
        {"Ergonomic Wooden Book Stand Riser", "Books and Stationery", 899.00, 45, "Multi-angle adjustable bamboo textbook holder for hands-free reading and recipe books.", "https://images.unsplash.com/photo-1544947950-fa07a98d237f?w=500"},
        {"Retractable Gel Ink Pens (Pack of 12)", "Books and Stationery", 349.00, 120, "0.5mm quick-dry black ink rollerball pens for effortless smudge-free note taking.", "https://images.unsplash.com/photo-1585336261026-78b17b629080?w=500"},
        {"Metal Mesh Desk Organizer Caddy", "Books and Stationery", 549.00, 80, "6-compartment stationery holder with pull-out drawer for pens, clips, and notepads.", "https://images.unsplash.com/photo-1586717791821-3f44a563fa4c?w=500"},
        {"Premium Watercolor Paint Set with Brushes", "Books and Stationery", 1199.00, 40, "36 vibrant artist-grade watercolor cakes in a portable metal tin with water brushes.", "https://images.unsplash.com/photo-1579783902614-a3fb3927b675?w=500"},
        {"Self-Adhesive Aesthetic Sticky Notes Palette", "Books and Stationery", 299.00, 130, "Pastel color sticky note pads and transparent index tabs for textbook annotation.", "https://images.unsplash.com/photo-1586075010923-2dd4570fb338?w=500"},
        {"Heavy Duty Desktop Paper Trimmer & Cutter", "Books and Stationery", 1399.00, 30, "A4 guillotine paper cutter with automatic safety guard and alignment grid.", "https://images.unsplash.com/photo-1569683795645-b62e50fbf103?w=500"},
        {"A4 Spiral Sketchbook 160 GSM (Pack of 2)", "Books and Stationery", 599.00, 70, "Heavyweight acid-free drawing paper suitable for pencils, charcoal, ink, and pastels.", "https://images.unsplash.com/photo-1544716278-ca5e3f4abd8c?w=500"},
        {"Electric Auto-Stop Pencil Sharpener", "Books and Stationery", 749.00, 60, "Battery & USB operated fast sharpener with heavy-duty helical blade for school and art.", "https://images.unsplash.com/photo-1585336261026-78b17b629080?w=500"},
        {"Desk Pad Blotter & Large Mouse Mat", "Books and Stationery", 699.00, 95, "Waterproof PU leather dual-sided desk protector pad for office and home study.", "https://images.unsplash.com/photo-1616763355548-1b606f439f86?w=500"},
        {"Luxury Wooden Bookmark Set with Tassels", "Books and Stationery", 399.00, 100, "Natural carved sandalwood bookmarks engraved with classic oriental motifs.", "https://images.unsplash.com/photo-1512820790803-83ca734da794?w=500"},

        // 6. Watches and Accessories (15 Products)
        {"Men's Minimalist Chronograph Watch", "Watches and Accessories", 2499.00, 45, "Quartz analog watch with black dial, 3 sub-dials, date window, and stainless steel mesh band.", "https://images.unsplash.com/photo-1524805444758-089113d48a6d?w=500"},
        {"Women's Rose Gold Diamond Bezel Watch", "Watches and Accessories", 2299.00, 40, "Elegant slim dress watch adorned with crystal accents and mother-of-pearl dial.", "https://images.unsplash.com/photo-1522335789203-aabd1fc54bc9?w=500"},
        {"Polarized Aviator Sunglasses UV400", "Watches and Accessories", 999.00, 80, "Classic metal frame sunglasses with anti-glare scratch-resistant TAC polarized lenses.", "https://images.unsplash.com/photo-1511499767150-a48a237f0083?w=500"},
        {"Genuine Leather RFID Blocking Men's Wallet", "Watches and Accessories", 899.00, 90, "Slim bifold wallet with 8 card slots, ID window, and currency notes compartment.", "https://images.unsplash.com/photo-1627123424574-724758594e93?w=500"},
        {"Women's Quilted Crossbody Shoulder Bag", "Watches and Accessories", 1799.00, 35, "Chic faux-leather handbag with gold chain strap and turn-lock flap closure.", "https://images.unsplash.com/photo-1584917865442-de89df76afd3?w=500"},
        {"Men's Automatic Reversible Leather Belt", "Watches and Accessories", 799.00, 85, "Full-grain leather dress belt with rotating buckle for black and brown styling.", "https://images.unsplash.com/photo-1624222247344-550fb60583dc?w=500"},
        {"Sterling Silver Zirconia Pendant Necklace", "Watches and Accessories", 1499.00, 50, "925 hallmarked silver delicate chain necklace with sparkling solitaire pendant.", "https://images.unsplash.com/photo-1599643478518-a784e5dc4c8f?w=500"},
        {"Unisex Canvas Duffle Travel Bag", "Watches and Accessories", 1999.00, 30, "Water-resistant weekender duffle bag with shoe compartment and detachable strap.", "https://images.unsplash.com/photo-1553062407-98eeb64c6a62?w=500"},
        {"Men's Stainless Steel Curb Chain Bracelet", "Watches and Accessories", 699.00, 75, "High-polish hypoallergenic silver-tone curb link wristband with lobster clasp.", "https://images.unsplash.com/photo-1611591475879-11f8e8156fb1?w=500"},
        {"Vintage Round Steampunk Sunglasses", "Watches and Accessories", 849.00, 70, "Retro circular metal sunglasses with side shields and UV400 protective lenses.", "https://images.unsplash.com/photo-1508296695146-257a814070b4?w=500"},
        {"Hard Shell Water-Resistant Laptop Backpack", "Watches and Accessories", 2499.00, 40, "Anti-theft 15.6 inch laptop bag with hidden USB port and TSA combination lock.", "https://images.unsplash.com/photo-1553062407-98eeb64c6a62?w=500"},
        {"Women's Leather Handheld Clutch Purse", "Watches and Accessories", 1199.00, 55, "Zip-around long continental wallet with multiple card slots and phone pocket.", "https://images.unsplash.com/photo-1566150905458-1bf1fc113f0d?w=500"},
        {"Unisex Blue Light Blocking Computer Glasses", "Watches and Accessories", 599.00, 100, "Lightweight TR90 frame anti-eyestrain glasses for screen glare protection.", "https://images.unsplash.com/photo-1591076482161-42ce6da69f67?w=500"},
        {"Men's Luxury Cufflinks and Tie Clip Set", "Watches and Accessories", 899.00, 60, "Classic silver and onyx formal cufflinks set in a velvet storage gift box.", "https://images.unsplash.com/photo-1611591475879-11f8e8156fb1?w=500"},
        {"Tactical Outdoor Sport Digital Watch", "Watches and Accessories", 1599.00, 50, "Shockproof military digital watch with compass, stopwatch, EL backlight, and 50M waterproof.", "https://images.unsplash.com/photo-1522335789203-aabd1fc54bc9?w=500"}
    };

    public static void seedProducts(Connection con, int sellerId) {
        String checkSql = "SELECT id FROM products WHERE name = ?";
        String insertSql = "INSERT INTO products (seller_id, name, category, price, quantity, description, image_url) VALUES (?, ?, ?, ?, ?, ?, ?)";

        int insertedCount = 0;
        try (PreparedStatement psCheck = con.prepareStatement(checkSql);
             PreparedStatement psInsert = con.prepareStatement(insertSql)) {

            for (Object[] p : CATALOG) {
                String name = (String) p[0];
                psCheck.setString(1, name);
                try (ResultSet rs = psCheck.executeQuery()) {
                    if (!rs.next()) {
                        psInsert.setInt(1, sellerId);
                        psInsert.setString(2, name);
                        psInsert.setString(3, (String) p[1]);
                        psInsert.setDouble(4, (Double) p[2]);
                        psInsert.setInt(5, (Integer) p[3]);
                        psInsert.setString(6, (String) p[4]);
                        psInsert.setString(7, (String) p[5]);
                        psInsert.addBatch();
                        insertedCount++;
                    }
                }
            }

            if (insertedCount > 0) {
                psInsert.executeBatch();
                System.out.println("ProductCatalogSeed: Inserted " + insertedCount + " new products into catalog.");
            } else {
                System.out.println("ProductCatalogSeed: All 100 products already exist in catalog.");
            }
        } catch (SQLException e) {
            System.err.println("Error seeding product catalog: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
