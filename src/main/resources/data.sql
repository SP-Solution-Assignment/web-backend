INSERT IGNORE INTO category (id, name) VALUES
(1, 'Politics'),
(2, 'Technology'),
(3, 'Sports'),
(4, 'Business'),
(5, 'Health'),
(6, 'Entertainment');

INSERT IGNORE INTO news (id, title, summary, content, author, published_at, image_url) VALUES
(101, 'Government Unveils New Digital Economy Policy',
 'The new policy aims to boost tech startups and streamline business registration.',
 'The government today announced a sweeping new digital economy policy designed to accelerate the growth of local tech startups. The policy includes tax incentives for early-stage companies, simplified business registration processes, and a national fund for digital infrastructure. Officials say the measures are expected to create thousands of jobs over the next five years and position the country as a regional hub for innovation.',
 'Nimal Perera', '2026-07-18 09:30:00', 'https://placehold.co/800x450?text=Digital+Economy+Policy'),

(102, 'National Cricket Team Wins Series Decider',
 'A thrilling final match sealed the series win in front of a packed home crowd.',
 'The national cricket team clinched a dramatic series victory in the final match, chasing down a target of 287 with just two wickets in hand. The captain praised the team''s composure under pressure and thanked fans for their support throughout the tournament. This win marks the team''s first series victory in over three years.',
 'Kasun Silva', '2026-07-19 14:15:00', 'https://placehold.co/800x450?text=Cricket+Series+Win'),

(103, 'Local Startup Raises $5M to Expand AI Health Platform',
 'The funding round will help the company expand its diagnostic tools across the region.',
 'A local health-tech startup has raised $5 million in a Series A funding round led by regional venture capital firms. The company''s AI-powered platform helps clinics with early diagnosis of common illnesses using image recognition. The founders say the new capital will be used to expand into three new countries and grow the engineering team.',
 'Amaya Fernando', '2026-07-17 11:00:00', 'https://placehold.co/800x450?text=AI+Health+Platform'),

(104, 'Award-Winning Film Director Announces New Project',
 'The acclaimed director revealed details of an upcoming feature film set in the highlands.',
 'The award-winning film director announced today the start of production on a new feature film set against the backdrop of the central highlands. The film, described as a family drama, is expected to begin shooting next month and will star a mix of established and emerging local actors. Industry watchers say the project could be a strong contender at next year''s film festivals.',
 'Dilani Jayasuriya', '2026-07-16 08:45:00', 'https://placehold.co/800x450?text=New+Film+Project'),

(105, 'Health Ministry Launches Nationwide Vaccination Drive',
 'The initiative targets rural communities with limited access to healthcare facilities.',
 'The Health Ministry has launched a nationwide vaccination drive aimed at improving coverage in rural and underserved communities. Mobile clinics will travel to remote areas over the next two months, offering free vaccinations and basic health screenings. Officials estimate the program will reach over 200,000 people by its conclusion.',
 'Ruwan Bandara', '2026-07-15 13:20:00', 'https://placehold.co/800x450?text=Vaccination+Drive'),

(106, 'Tech Giant Announces Layoffs Amid Restructuring',
 'The company cited shifting market conditions and a focus on AI investments.',
 'A major technology company announced today it will cut approximately 4,000 jobs globally as part of a broader restructuring effort. In a memo to employees, the CEO said the company is redirecting resources toward artificial intelligence research and cloud infrastructure. Affected employees will receive severance packages and extended healthcare benefits.',
 'Sanduni Rathnayake', '2026-07-14 16:00:00', 'https://placehold.co/800x450?text=Tech+Layoffs');

-- Many-to-many mappings (mirrors categoryIds in the frontend dummy data)
INSERT IGNORE INTO news_category (news_id, category_id) VALUES
(101, 1), (101, 2), (101, 4),
(102, 3),
(103, 2), (103, 4), (103, 5),
(104, 6),
(105, 5), (105, 1),
(106, 2), (106, 4);
