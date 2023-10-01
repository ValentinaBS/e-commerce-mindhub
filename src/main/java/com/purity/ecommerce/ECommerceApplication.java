package com.purity.ecommerce;

import com.purity.ecommerce.models.Customer;
import com.purity.ecommerce.models.Product;
import com.purity.ecommerce.repositories.CustomerRepository;
import com.purity.ecommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ECommerceApplication {

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(ECommerceApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ProductRepository productRepository, CustomerRepository customerRepository){
		return (args) -> {
			Customer customer1 = new Customer("Quione", "quione@mindhub.com", passwordEncoder.encode("123456"), "Cl 1A 5B 53");
			customerRepository.save(customer1);
			Customer admin = new Customer("admin", "admin@mindhub.com", passwordEncoder.encode("123456"), "Cl 1A 5B 53");
			customerRepository.save(admin);

			Product productBrushes1 = new Product("Ultra Plush Powder Brush", "Real Techniques Ultra Plush Powder Makeup Brush helps you create a flawless finish with its large, domed shape for all-over application. RT 201 powder brush has a plush large head for sheer application and a smooth, mattified finish. Best used with powder foundations, powder bronzers, and setting powders. Provides smooth, high-definition results with tapered bristles to help blend powders seamlessly. Extended aluminum ferrules that are light weight, easy to use, and color coded. 100% Cruelty-Free and Vegan. Flawless Results. Easy to clean with Real Techniques Brush Cleansing Gel or Spray. Long Lasting Makeup Application. UltraPlush Synthetic Bristles.", "Long Lasting Makeup Application.", 6.99, "brushes", "Real Techniques", 6, "https://i.imgur.com/xbcGA7J.jpg");
			Product productBrushes2 = new Product("Complexion Duo Brush", "2-IN-1 MAKEUP BRUSH: The e.l.f. Complexion Duo Brush is the only makeup brush you need for a flawless, airbrushed finish. This 2-in-1 brush is designed for the application of foundation and concealer. PRECISION APPLICATION: This face brush features a larger end for applying your foundation while the smaller end lends itself for precise application for the smaller areas you want to conceal. MULTI-USE: Ideal for use with liquids and powders. PERFECT PAIRING: e.l.f. Complexion Duo Brush is the only tool you need to apply Camo CC Cream Foundation and 16HR Camo Concealer. 100% VEGAN & SYNTHETIC BRISTLES: All e.l.f. brushes are made of synthetic, vegan fibers. All e.l.f. products are 100% cruelty-free and Vegan.", "Ideal for liquids and powders.", 7.50, "brushes", "e.l.f.", 14, "https://i.imgur.com/TVxloww.jpg");
			Product productBrushes3 = new Product("Flawless Face Brush", "CREATES A NATURAL LOOK: The e.l.f. Flawless Face Brush is a soft, synthetic brush that lets you apply product with the lightest touch for a soft, sheer, natural-looking effect. PRECISE APPLICATION: It has a slight point at the tip that fits nicely into the contours of the face for more precise placement. MULTI-USE & VERSATILE: This 2-sided brush allows you to use the flat side for all over application of powders, or the skinny side for blush and bronzer. 100% VEGAN & SYNTHETIC BRISTLES: All e.l.f. brushes are made of synthetic, vegan fibers. All e.l.f. products are 100% cruelty-free and Vegan. CARE INSTRUCTIONS: Cleanse regularly with the e.l.f. Cosmetics Daily Brush Cleaner (not live on Amazon right now) e.l.f. Cosmetics Brush Shampoo to maintain perfect color application and to increase the longevity of your brush.", "Made of synthetic, vegan fibers.", 4.20, "brushes", "e.l.f.", 10, "https://i.imgur.com/yC5b78Q.jpg");
			Product productBrushes4 = new Product("Retractable Face Brush", "The EcoTools Retractable Makeup Brush is designed with full, soft bristles for a versatile application of makeup on-the-go.\n" +
					"Designed for sensitive skin and versatile application of powder and bronzer.\n" +
					"Simply remove the cap and dab the brush in the makeup product.\n" +
					"Includes cap for mess-free storage.\n" +
					"Best used with powders and bronzers.\n" +
					"Beautifully soft bristles, for gentle makeup application.\n" +
					"Handcrafted with eco-friendly materials.\n" +
					"Made with synthetic Taklon bristles and sleek aluminum ferrules.\n" +
					"Cruelty-Free and Vegan - EcoTools products are never tested on animals.\n" +
					"Made with recycled materials - EcoTools believes in reducing waste by using recycled aluminum and plastic in our products.\n", "Flawless, airbrushed finish.", 7.50, "brushes", "EcoTools", 14, "https://i.imgur.com/mPIx14j.jpg");
			Product productBrushes5 = new Product("Duo Makeup Brush Kit", "The EcoTools Eye Enhancing Set is designed with 4 brush heads to shade, define, blend and smudge. Use the shade end to apply shadow all over the lid with the soft, tapered bristles. Define your eye look with the angled liner to line and enhance eye shape. Use the blend end to blend and blur shadow to eliminate streaks and harsh lines. Smudge shadow and liner along the lash line. Beautifully soft bristles, for gentle makeup application. Handcrafted with eco-friendly materials and lightweight bamboo handles. Made with synthetic Taklon bristles and sleek aluminum ferrules. 100% Cruelty Free and Vegan - EcoTools products are never tested on animals. Made with recycled materials - EcoTools believes in reducing waste by using recycled aluminum and plastic in our products.", "Beautifully soft bristles.", 4.90, "brushes", "EcoTools", 24, "https://i.imgur.com/kgpBkPZ.jpg");
			Product productBrushes6 = new Product("Skin Perfecting Brush", "The EcoTools Skin Perfecting Making Brush is designed with domed, angled bristles to blur imperfections for an airbrushed base. Works with powder, liquid, or cream foundations for a natural finish Domed shape allows for precise application. Soft construction creates an airbrushed-look foundation base. The skin perfecting brush is great to achieve low coverage for a more natural look. Beautifully soft bristles, for gentle makeup application. Handcrafted with eco-friendly materials and lightweight bamboo handles. Made with synthetic Taklon bristles and sleek aluminum ferrules. Cruelty Free and Vegan - EcoTools products are never tested on animals. Made with recycled materials - EcoTools believes in reducing waste by using recycled aluminum in our products.", "Low coverage, a more natural look.", 7.99, "brushes", "EcoTools", 33, "https://i.imgur.com/WgqvxET.jpg");
			Product productBrushes7 = new Product("Wonder Cover Brush", "The EcoTools Wonder Cover Complexion Foundation Makeup Brush is designed for full coverage in less time. Created with 100,000 bristles and an oval head for a natural looking coverage. Lightly dampen the bristles for a more sheer application. Can be used wet for a sheer application or dry for more coverage. With a simple sweep, camouflage common skin issues like rosacea, hyperpigmentation and blemishes. Best used with liquid or cream foundations for great results. Handcrafted with eco-friendly materials and lightweight bamboo handles. Made with synthetic Taklon bristles and sleek aluminum ferrules. 100% Cruelty Free and Vegan - EcoTools products are never tested on animals. Made with recycled materials - EcoTools believes in reducing waste by using recycled aluminum in our products.", "Camouflage common skin issues.", 6.20, "brushes", "EcoTools", 8, "https://i.imgur.com/tyjYsP4.jpg");
			Product productBrushes8 = new Product("Putty Bronzer Brush", "PERFECT FOR OUR PUTTY BRONZER: The best makeup brush for applying our Putty Bronzer! With its unique angled brush head and semi-stiff bristles, this brush is designed to pick up and distribute the perfect amount of product while hugging your cheeks. CONTOUR & HIGHLIGHT: Contour and highlight with control using the 100% synthetic, soft bristles, which blend the putty-to-powder formula into a smooth, even finish. FLAWLESS FINISH: Our Putty Bronzer brush easily picks up, distributes, and blends product for flawless, effortless application. Swirl brush into the putty bronzer. Then stipple and blend onto the hollows of your cheeks. 100% VEGAN & SYNTHETIC BRISTLES: All e.l.f. brushes & sponges are made of synthetic, vegan fibers. All e.l.f. products are 100% cruelty-free and Vegan. SKIN-LOVING INGREDIENTS: All e.l.f. products are made from skin-loving ingredients you want, minus the toxins you don’t—all at good-for-you prices. All e.l.f. products are 100% cruelty-free and Vegan.", "Flawless, effortless application.", 4.00, "brushes", "e.l.f.", 47, "https://i.imgur.com/qNSBKUP.jpg");
			Product productBrushes9 = new Product("Kabuki Face Brush", "This synthetic haired brush is soft and absorbent, making it perfect to use with wet or dry products. Allows for precise and controlled application of bronzer, powder, or highlighter. Compact design is ideal for travel and on-the-go use. For best results, clean or wipe brush daily. Cruelty-free, vegan and 100% free from Phthalates, Parabens, Nonylphenol, Ethoxylates, Triclosan, triclocarban.", "Soft and absorbent.", 7.00, "brushes", "e.l.f.", 19, "https://i.imgur.com/8wJPgPM.jpg");
			Product productBrushes10 = new Product("Domed Stipple Brush", "FLAWLESSLY BLENDS PRODUCT: This Domed Stipple Brush distributes product evenly by blurring product into the skin in a stippling process for a soft-focus “selfie ready” effect. VERSATILE USES: Our Domed Stipple Brush creates a seamless looking finish for foundation, concealer, and any other cream, liquid, or powder products. HOW TO USE: Dip the Selfie Ready Stipple Brush into product and apply to the face in a dotting or stippling motion.Use back and forth motions to blend. 100% VEGAN & SYNTHETIC BRISTLES: All e.l.f. brushes & sponges are made of synthetic, vegan fibers. All e.l.f. products are 100% cruelty-free and Vegan. SKIN-LOVING INGREDIENTS: All e.l.f. products are made from skin-loving ingredients you want, minus the toxins you don’t—all at good-for-you prices. All e.l.f. products are 100% cruelty-free and Vegan.", "Seamless looking finish.", 7.00, "brushes", "e.l.f.", 25, "https://i.imgur.com/31vbbyK.jpg");
			Product productBrushes11 = new Product("Mini Multitask Brush", "The Real Techniques Mini Multitask Brush is perfect for sweeping, swirling, and diffusing during your makeup application. Our RT 407 brush head has plush, tapered bristles for soft focus application. Features a full-sized head and compact handle, making this brush perfect for traveling. Use with powder blush, bronzer, or setting powder. Extended aluminum ferrules that are light weight, easy to use, and color coded. 100% Cruelty-Free and Vegan. Flawless Results. Easy to clean with Real Techniques Brush Cleansing Gel or Spray. Long Lasting Makeup Application. UltraPlush Synthetic Bristles.", "Easy to clean.", 4.20, "brushes", "Real Techniques", 4, "https://i.imgur.com/Jzx1BzF.jpg");
			Product productBrushes12 = new Product("Foundation Makeup Blender", "The Real Techniques Foundation Makeup Blender features small, dense bristles for blending & buffing for flawless coverage that's perfect for liquid, cream, & powder foundations. RT 213 features dense, domed bristles that blend and buff for even coverage, and can be used with bronzers. Provides smooth, high-definition results with small, dense bristles to blend and buff for flawless coverage. Best used with liquid, cream, or powder foundation. Blend, dab, and bluff for controlled blending. Light weight, easy to use, and color coded. 100% Cruelty-Free and Vegan. Easy to clean with Real Techniques Brush Cleansing Gel or Spray. UltraPlush Synthetic Bristles for flawless results. Long Lasting Makeup Application.", "Smooth, high-definition results.", 9.10, "brushes", "Real Techniques", 0, "https://i.imgur.com/hJCEnub.jpg");
			Product productBrushes13 = new Product("Bamboo Brush Set", "Pro Bamboo Makeup Brush set : Premium synthetic Laser cut superfine microfiber with wooden thick handles. Brush sizes and thickness varies. Include Powder foundation brush, Concealer brush, Eyelash eyebrow brush, Blush brush, Bronzer brush, Sponge applicator and Angled liner brush. Real wooden handles, Vegan hair and materials, Semi Soft synthetic bristles with natural cotton pouch which holds 7 makeup brushes. To get the best results, Rinse and clean with SHANY natural makeup brush cleanser before the first use, Brush instruction included in the box. Designed in the U.S.A by professional makeup artists. SHANY is a proud member of PETA. All of our products are cruelty free.", "Easy to clean.", 17.15, "brushes", "SHANY", 29, "https://i.imgur.com/SVubv4d.jpg");
			Product productBrushes14 = new Product("Travel Fantasy Brush Kit", "The Real Techniques Travel Fantasy Mini Brush kit includes 10 makeup brushes and a cosmetic bag for on-the-go glam. Includes: (1) RT 210 Mini Expert Concealer Brush, (1) RT 407 Mini Multitask Brush, (1) RT 206 Mini Contour Brush, (1) RT 406 Mini Medium Sculpting Brush, (1) RT 308 Mini Medium Shadow Brush, (1) RT 313 Mini Definer Brush, (1) RT 343 Mini Spoolie, (1) RT 332 Mini Smudge Brush, (1) RT 301 Mini Base Shadow Brush, (1) RT 307 Mini Shading Brush, and a Mini Makeup Bag. Makeup brushes can be used with any liquid, cream, and powder makeup products. Multiuse brushes best used with foundations, eyeshadows, concealers, blushes, bronzers, and more. Create any look you desire whether that’s bold and bright, or natural shimmer. Small brushes are easy to take with you wherever you go- whether it's at work or across the world! Store all your beauty tools and brushes in the travel cosmetic bag. UltraPlush custom-cut, synthetic bristles for superior product pickup and laydown. 100% Cruelty-Free and Vegan- Real Techniques products are never tested on animals. Designed for long-lasting makeup application.", "Easy to take with you.", 26.99, "brushes", "Real Techniques", 2, "https://i.imgur.com/JIjjYiz.jpg");
			Product productBrushes15 = new Product("Premium Brush Set", "【14-PIECE BRUSH COLLECTION】 Cover all your needs of all Your Makeup. Using BS-MALL blending brush, eyebrow brush, eyeshadow brushes and so on can give you results similar to makeup applied by a professional makeup artist!【FIT EVERY FACE】The eye makeup brushes come in a variety of shapes and sizes hence suitable for contouring & highlighting for every face shape.【Vegan & Cruelty-Free Bristles】 The bristles are well made of durable synthetic fiber - soft and silky nylon, ensuring long time use. All the makeup brushes are fine and dense, suitable for even the most sensitive skin. With a great holding power of powder, the brushes would be a good helper to create a flawless look.【STYLISH DESIGN】The 14 pcs makeup brush set looks very chic and fashionable with the most popular and elegant Rose Gold color , a must-have in your cosmetic bag.【Ideal For Gift】 Intended for makeup beginners and enthusiasts as the makeup brush kit is easy to use and superior in quality at an affordable price, a complete assortment to allow you to keep your makeup. Selected gift for mom, wife or female friends.", "Ideal for a gift.", 11.25, "brushes", "BS-MALL", 1, "https://i.imgur.com/KroTGll.jpg");
			Product productBrushes16 = new Product("Precious Metals Brush Set", "The EcoTools Brightening Eye Set has great eye brush essentials for you to achieve a chic eye look. The Precious Metals Brightening Eye Set includes (1) Angled Liner Brush, (1) Spoolie Brush, (1) Blending Crease Brush, (1) Crease Shadow Brush, and (1) Precision Smudge Brush. 4 essential makeup brushes that can be used in a variety of ways to make your eye look pop. Use our Brightening Eye set with liquid, cream or powder products. Get beautiful, bold eyeshadow looks using the Brightening Eye kit. The Brightening Eye Set is a part of our Precious Metals limited-edition collection which is inspired by earth’s natural metals. The set includes brushes made with 60% recycled aluminum- Aluminum is infinitely recyclable, helps reduce emissions, and is earth’s most abundant metal. The Brightening Eye kit is made with 100% recycled synthetic hair. Made with recycled materials - EcoTools believes in reducing waste by using recycled aluminum and plastic in our products. 100% Cruelty-Free and Vegan- EcoTools products are never tested on animals.", "Achieve a chic-eye look.", 11.90, "brushes", "EcoTools", 36, "https://i.imgur.com/p4UxFm0.jpg");

			Product productSkincare1 = new Product("Holy Hydration! Face Cream", "SPF 30 PROTECTION: Our best selling face cream now with SPF 30 sun protection you can wear daily! Hydrate and protect with this lightweight, ingredient-driven moisturizer that delivers lasting hydration, SPF 30 protection, and instantly absorbs into the skin, making it perfect for wearing under makeup. POWERFUL HYDRATION BOOST: This ultra-hydrating SPF face cream helps replenishes moisture for supple skin and evens out skin tone. Plus, the non-greasy formula instantly sinks into the skin for a smooth, super-soft feel and is great for any day. NOURISHES & PLUMPS SKIN: Key ingredients include Broad Spectrum SPF 30 to help protect the skin from harsh UVA/UVB rays, Hyaluronic Acid to lock in moisture for a plump complexion, Niacinamide to minimize pores & even out skin tone, and Peptides to promote collagen & improve skin texture. FOR EVERYONE: Good for normal, dry, combo and oily skin types. SKIN-LOVING INGREDIENTS: All e.l.f. products are made from skin-loving ingredients you want, minus the toxins you don’t—all at good-for-you prices. All e.l.f. products are 100% cruelty-free and Vegan.", "Hydrate and protect.", 12.00, "skincare", "e.l.f.", 17, "https://i.imgur.com/CyrIMXR.jpg");
			Product productSkincare2 = new Product("D.E.J. Face Cream", "An intensive skin-renewing moisturizer inspired by the Dermal-Epidermal Junction (DEJ), a critical factor in skin aging, that helps improve the visible signs of aging from multiple pathways and features. Prebiotic Innovation to promote a healthy Microbiome. Targets the appearance of fine lines and wrinkles. Visibly adds volume and plumps skin. Brightens and evens skin tone. Promotes a healthy Microbiome. Provides antioxidant benefits to help combat environmental stressors. Provides intense hydration with a lightweight feel. 1.7 oz w/pump", "Prebiotic Innovation.", 162.00, "skincare", "Revision", 31, "https://i.imgur.com/wdyqJ78.jpg");
			Product productSkincare3 = new Product("Firming Night Treatment", "This peptide-rich, age-defying cream provides intense short- and long-term moisturization while you sleep. Contains two peptides to reduce the look of fine lines and wrinkles. Includes Sodium Hyaluronate, which binds moisture to the skin. Alleviates dryness with Shea Butter and a blend of botanical extracts. Rehydrates, replenishes and brightens dull, dry skin. 1.0 oz jar Who benefits? Dry, sensitive and mature skin types.", "Alleviates dryness.", 90.00, "skincare", "Revision", 11, "https://i.imgur.com/pMRTfWX.jpg");
			Product productSkincare4 = new Product("Lumiquin Hand Cream", "Take time off your hands with this nighttime formula packed with powerful age-defying ingredients and antioxidants. Brightens the skin and minimizes the appearance of imperfections. Enhances skin's moisture level so hands look smoother. Supports skin's natural moisture barrier. 1.7 oz tube Who benefits? All skin types. Ideal for those with dry skin. How to Use Lumiquin is designed to be used once daily at night. To be effective, this product needs to be on the skin for several hours without hand washing. You can continue to use a regular hand cream during the day. Cleanse hands. Apply one pearl-sized drop onto the back of each hand before bedtime. For best results, apply sunscreen to hands during the day.", "For brighter hands.", 61.00, "skincare", "Revision", 42, "https://i.imgur.com/ssA4eVH.jpg");
			Product productSkincare5 = new Product("Happy Hydration Cream", "RICH, LUXE & CREAMY MOISTURIZER: e.l.f. Happy Hydration Cream is a rich, buttery face moisturizer that locks in moisture for ultra-hydrated skin, and feels like luxury on your face. NOURISHES & PLUMPS SKIN: This face cream easily blends into skin, helping it to retain moisture for lasting hydration while restoring youthful bounce in your complexion. ULTRA-HYDRATING INGREDIENTS: Infused with Vitamin B5 to soothe & calm, Hyaluronic Acid to lock in moisture for a plump complexion, and Niacinamide to help minimize pores & even out skin tone. FOR ALL SKIN TYPES: The e.l.f. Happy Hydration Cream is perfectly compatible with all skin types. SKIN-LOVING INGREDIENTS: All e.l.f. products are made from skin-loving ingredients you want, minus the toxins you don’t—all at good-for-you prices. All e.l.f. products are 100% cruelty-free and Vegan.", "Ultra-hydrating ingredients.", 12.00, "skincare", "e.l.f.", 29, "https://i.imgur.com/EVERTgP.jpg");
			Product productSkincare6 = new Product("Holy Hydration! Sleep Mask", "POWERFUL HYDRATION BOOST: Treat your skin to a good night’s sleep and wake up with a refreshed, hydrated complexion with e.l.f. Holy Hydration! Sleep Mask. This rich, creamy mask is your skin’s “reset button,” delivering a surge of moisture and replenishing dry and dehydrated skin. DUAL-USE FACE MASK: This ultra-hydrating mask can be used as a rinse-off product or as an overnight mask to promote a plumped up, radiant complexion, keep skin feeling soft and supple, and soothe & nourish skin while you sleep! NOURISHES & REPLENISHES SKIN: Key ingredients include Hyaluronic Acid to lock in moisture for a plump complexion, Ceramides to build up the skin’s moisture barrier, and Peptides to promote collagen and improve skin texture. FOR EVERYONE: Great for normal, dry, combination, and oily skin. Use 2-3x a week. SKIN-LOVING INGREDIENTS: All e.l.f. products are made from skin-loving ingredients you want, minus the toxins you don’t—all at good-for-you prices. All e.l.f. products are 100% cruelty-free and Vegan.", "Dual-use face mask.", 9.95, "skincare", "e.l.f.", 63, "https://i.imgur.com/fWPpkgj.jpg");
			Product productSkincare7 = new Product("Daily Hydration Moisturizer", "LOCK IN MOISTURE: The e.l.f. Daily Hydration Moisturizer is lightweight and deeply nourishing for smoother, suppler skin. ANTIOXIDANT-INFUSED: Infused with Aloe to help soothe the skin, Jojoba Oil & Shea Butter help hydrate the skin, Vitamin E to soften skin and Grapeseed Oil to even out skin tone. LIGHTWEIGHT, CREAMY FORMULA: Absorbs easily and nourishes the skin without weighing it down, revealing a naturally radiant complexion. FOR EVERYONE: Perfect for all skin types. SKIN-LOVING INGREDIENTS: All e.l.f. products are made from skin-loving ingredients you want, minus the toxins you don’t—all at good-for-you prices. All e.l.f. products are 100% cruelty-free and Vegan.", "Antioxidant-infused.", 8.00, "skincare", "e.l.f.", 44, "https://i.imgur.com/Gp8d0nd.jpg");
			Product productSkincare8 = new Product("SKIN Youth Boosting", "THE ULTIMATE ANTI-AGING SERUM: Introducing your skin’s e.l.f.ing game changer. The most powerful retinoid you can get without a ’script reduces the appearance of fine lines and wrinkles over time. CREATES A RADIANT COMPLEXION: With continued use, this results-driven night serum works to reveal rejuvenated, smooth and radiant skin. INFUSED WITH ANTIOXIDANTS: The potent formula is infused with a Retinoid + Antioxidant Complex to help reduce the appearance of fine lines and wrinkles and Hyaluronic Acid to help moisturize skin for a plump complexion. HOW TO USE: On clean skin, blend pea-sized amount onto your face and neck at night. Note that the amber color of the formula is a naturally occurring hue due to the potency of the retinoid complex. It will blend into the skin after application. SKIN-LOVING INGREDIENTS: All e.l.f. products are made from skin-loving ingredients you want, minus the toxins you don’t—all at good-for-you prices. All e.l.f. products are 100% cruelty-free and Vegan.", "Ultimate anti-aging serum.", 22.00, "skincare", "e.l.f.", 21, "https://i.imgur.com/Je2qBhO.jpg");
			Product productSkincare9 = new Product("Pure Skin Cleanser", "HYDRATING & GENTLE FACIAL CLEANSER: Our non-foaming cream cleanser gently cleanses to leave your skin feeling refreshed, hydrated, and squeaky clean. CLEANSES WITHOUT IRRITATION: Pure Skin Cleanser effectively removes dirt, oil and impurities without irritation. Gentle enough for all skin types- even sensitive skin. NOURISHING ESSENTIALS: Made with Oat Milk to nourish & soothe skin, Niacinamide to shrink pores & even out skin tone, Allanoin to soothe & skin, Ceramides to build up the skin's moisture barrier & Hyaluronic Acid to lock moisture for a plump complexion. HOW TO USE: Gently massage 1-2 pumps onto damp skin, rinse and pat dry. Use morning and night. Pairs well with e.l.f. Pure Skin Toner and e.l.f. Pure Skin Moisturizer. DERMATOLOGIST DEVELOPED: e.l.f. Pure Skin Cleanser is dermatologist-developed, fragrance-free, non-comedogenic, cruelty-free, and vegan.", "Hydrating and gentle.", 6.30, "skincare", "e.l.f.", 8, "https://i.imgur.com/OcZjlaz.jpg");
			Product productSkincare10 = new Product("Revitalift Moisturizer", "Revitalift Triple Power Daily Moisturizer is a fragrance free, anti-aging face moisturizer to visibly reduce wrinkles, firm and brighten skin in 1 week. Formulated with 3 of the top derm-recommended ingredients - Pro-Retinol, Hyaluronic Acid and Vitamin C. After use, skin feels softer, smoother and plumped with hydration - in 1 week, wrinkles are visibly reduced, firmness is improved and skin tone is brighter. Dermatology tested for safety, absorbs quickly, non-greasy, does not clog pores and suitable for sensitive skin - paraben free, mineral oil free, dye free, allergy tested, fragrance free. How to use - Apply to face and neck in the morning or night after cleansing and applying a serum- layers well under makeup.", "Absorbs quickly, non-greasy.", 22.00, "skincare", "L’Oréal Paris", 10, "https://i.imgur.com/mKQDJQv.jpg");
			Product productSkincare11 = new Product("True Match Lumi Glotion", "Glow Enhancing Make Up: Instantly hydrate and luminize your skin for an all over, natural glow with Lumi Glotion; This liquid face and body highlighter delivers an illuminating color tint to enhance your skin’s natural glow. Hydrating Makeup Highlighter: Our glow lotion is infused with glycerin and shea butter for all day hydration; Available in 4 shades for all skin tones, the glow drops can be worn alone, under foundation, or on target areas for a more luminous effect. Liquid Highlighter to Glow: Use Lumi Glotion illuminator to get a healthy, sun kissed glow with no sun; Try our complete line of bronzing drops, bronzers, luminizers, glow enhancing lotions. Because You're Worth It: Create the look you want with our full line of makeup including foundations, concealers, highlighter makeup, brow pencils, eyeshadow palettes, lipsticks and much more. L'Oreal Paris Beauty: A leading total beauty care company based in Paris, we offer innovative products and unique expertise from beauty experts in makeup, skin care, hair care, styling and hair color.", "Glow for your skin.", 13.00, "skincare", "L’Oréal Paris", 19, "https://i.imgur.com/rlvhS2E.jpg");
			Product productSkincare12 = new Product("Age Perfect Eye Cream", "Age Perfect Cell Renewal Eye Cream with Vitamin E is an anti-aging eye treatment that targets 5 signs of aging at once including dark circles, bags, puffiness, lines and crepey lids. Reveal millions of new cells day after day - made with a patented Antioxidant and Vitamin E to help recover skin's antioxidant reserves and boost its natural renewal process. This eye treatment absorbs quickly and provides 24 hours of hydration with no greasy or sticky finish. Dermatologist Tested - suitable for sensitive skin, this formula is allergy tested, fragrance free, paraben free and mineral oil free - suitable for contact lens wearers. Pair with New Age Perfect Cell Renewal Midnight Serum, a breakthrough anti-aging treatment that is preferred to the Number 1 Luxury Night Serum.", "24 hours of hydration.", 16.50, "skincare", "L’Oréal Paris", 26, "https://i.imgur.com/CniEu2d.jpg");
			Product productSkincare13 = new Product("Age Perfect Balm", "Age Perfect Hydra-Nutrition All-Over Balm is a silky balm for mature, very dry skin – soothes and rescues dry face, neck, chest, and hands – helps strengthen skin's ability to retain nourishing moisture and relieve dryness on face, neck, chest, and hands. All-Over Balm wraps skin in soothing moisture to soften skin that feels right and uncomfortable – beyond nourishment, skin is left looking more youthful with a healthy glow. Immediately, skin feels soothed, softer, smoother, more supple – by the end of the day, skin feels nourished, more comfortable, less tight – after 1 month of use, dry, fine lines appear reduced. Age Perfect Hydra-Nutrition All-Over Honey Balm with Manuka Honey Extract - Paraben Free, Mineral Oil Free, Non-Sticky, was Dermatologist tested for safety and is suitable for sensitive skin. For best results, use balm twice daily on clean skin - can be used over your daily moisturizer, ideal for face, neck, chest and hands.", "Soothes and rescues dryness.", 11.40, "skincare", "L’Oréal Paris", 34, "https://i.imgur.com/HDBMnsR.jpg");
			Product productSkincare14 = new Product("Creme Body", "Intensively Moisturizes: This NIVEA skin creme intensively moisturizes, nourishes and protects skin from that rough, dry feel. Provitamin B5 Enriched: This NIVEA moisturizer cream is enriched with Provitamin B5 to nourish dry skin. Multi-Use: NIVEA cream can be used as a body cream, hand cream and face cream for soft, smooth skin anytime. Great for Rough Spots: NIVEA Creme can be used all over the body, and is best for rough spots such as knees, feet, elbows and hands. Includes one (1) 16 ounce jar of NIVEA Creme Body, Face and Hand Moisturizing Cream.", "Intensively moisturizes.", 11.70, "skincare", "NIVEA", 4, "https://i.imgur.com/2X2cqJt.jpg");
			Product productSkincare15 = new Product("Skin Firming Body Lotion", "48-Hour Moisture: Specially formulated for darker skin tones to replenish nourishing moisture. Firmer Skin: Firms skin and improves skin elasticity in as little as two weeks(1) (with twice-daily application). Rich in Omegas: Skin firming body lotion contains Q10, natural Argan Oil (the 'liquid gold' of oils) rich in Omega 6 and 9 fatty acids Revitalizes and Nourishes: This NIVEA body lotion revitalizes and nourishes dry, ashy skin for 9 out of 10 women with melanin-rich skin(1). Long-Lasting Moisture: Gentle, non-greasy formula absorbs quickly and provides 48-hour moisture. Leave Your Skin Radiant: This moisturizing lotion leaves skin smooth, soft and radiantly beautiful. Skin Compatibility Tested: Dermatologically tested for skin compatibility. Trusted Brand: We use our 130 years of skincare expertise to carefully select high quality ingredients. Includes one (1) 16.9 fluid ounce bottle of NIVEA Skin Firming Melanin Beauty and Hydration Body Lotion with Q10 and Argan Oil.", "48-Hour Moisture.", 9.99, "skincare", "NIVEA", 44, "https://i.imgur.com/aLzidhP.jpg");
			Product productSkincare16 = new Product("Deep Clean Facial Cream", "7-fluid ounce tube of Neutrogena Deep Clean Cream Facial Cleanser to help improve dull complexion, for skin that looks and feels healthier. This daily facial cleanser is made with beta hydroxy acid and goes deep down into pores to dissolve dirt, oil and makeup, and wash away dead surface cells. Oil-free and alcohol-free, the face wash features a creamy, deep-cleansing lather that won't dry your skin. It's dermatologist-tested and rinses clean without leaving behind any pore-clogging residue, for fresher, healthier-looking skin. From a dermatologist recommended skincare brand, the deep clean face wash is non-comedogenic so it won't clog pores.", "Oil and alcohol-free.", 5.80, "skincare", "Neutrogena", 0, "https://i.imgur.com/owqsFsk.jpg");

			productRepository.save(productBrushes1);
			productRepository.save(productBrushes2);
			productRepository.save(productBrushes3);
			productRepository.save(productBrushes4);
			productRepository.save(productBrushes5);
			productRepository.save(productBrushes6);
			productRepository.save(productBrushes7);
			productRepository.save(productBrushes8);
			productRepository.save(productBrushes9);
			productRepository.save(productBrushes10);
			productRepository.save(productBrushes11);
			productRepository.save(productBrushes12);
			productRepository.save(productBrushes13);
			productRepository.save(productBrushes14);
			productRepository.save(productBrushes15);
			productRepository.save(productBrushes16);

			productRepository.save(productSkincare1);
			productRepository.save(productSkincare2);
			productRepository.save(productSkincare3);
			productRepository.save(productSkincare4);
			productRepository.save(productSkincare5);
			productRepository.save(productSkincare6);
			productRepository.save(productSkincare7);
			productRepository.save(productSkincare8);
			productRepository.save(productSkincare9);
			productRepository.save(productSkincare10);
			productRepository.save(productSkincare11);
			productRepository.save(productSkincare12);
			productRepository.save(productSkincare13);
			productRepository.save(productSkincare14);
			productRepository.save(productSkincare15);
			productRepository.save(productSkincare16);
		};
	}
}
