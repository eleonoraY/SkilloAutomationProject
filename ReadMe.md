Тест 1: ChangeFileStatusToPrivateTest: Промяна на статуса на файл от public към private

Цел на теста:
Тестът има за цел да провери дали качените файлове могат да бъдат променени от public на private, така че да не бъдат видими в публичния списък на потребителя.

Стъпки на теста:
1. Влизане в профила на потребителя.
2. Навигация до страницата с качените файлове.
3. Кликване върху качен файл.
4. Натискане на бутона за заключване на файла (за да се промени неговия статус на private).
5. Проверка дали файлът е изчезнал от публичния списък.

Очакван резултат:
- След извършване на действията, файлът трябва да бъде скрит от публичния списък на файловете. Тестът ще потвърди това чрез проверка на висимостта на файла.
--------------------------------------------------------------------------------------------

Тест 2: FollowUnfollowUserTest: Следване и Отписване от потребител

Цел на теста:
Тестът има за цел да провери дали функционалността за следване и отписване на потребители работи правилно. Тестът проверява дали броят на последваните потребители се актуализира коректно след натискане на бутоните "Follow" и "Unfollow".

Стъпки на теста:
1. Влизане в профила на потребителя.
2. Търсене на друг потребител и отваряне на неговия профил.
3. Натискане на "Follow", за да се последва потребителят.
4. Проверка дали броят на последваните потребители се е увеличил.
5. Натискане на "Unfollow", за да се отписва от потребителя.
6. Проверка дали броят на последваните потребители се е намалил.
7. Проверка дали бутонът за последване е променен на "Unfollow" след натискане на "Follow", и обратно след "Unfollow".

Очакван резултат:
- След натискане на "Follow", броят на последваните потребители трябва да се увеличи с 1.
- След натискане на "Unfollow" броят на последваните потребители трябва да се намали с 1.
- След натискане на "Follow", бутонът трябва да се промени на "Unfollow".
--------------------------------------------------------------------------------------------

Тест 3: RegistrationTest: Регистрация на потребител

Цел на теста:
Тестът има за цел да провери дали функционалността за регистрация на нов потребител работи правилно. Тестът включва проверка дали регистрационната страница се зарежда, дали всички полета са попълнени правилно и дали потребителят е успешно пренасочен към началната страница след успешна регистрация.

Стъпки на теста:
1. Зареждане на страницата за регистрация.
2. Попълване на полетата за потребителско име, имейл, парола и потвърждение на паролата.
3. Кликване на бутона за регистрация.
4. Проверка дали потребителят е пренасочен към началната страница.
5. Навигация към профила и проверка дали името на потребителя е видимо.

Очакван резултат:
- Регистрационната страница трябва да се зареди без грешки.
- Потребителят трябва да бъде успешно регистриран и пренасочен към началната страница.
- Името на потребителя трябва да бъде видимо в профила след регистрацията.
--------------------------------------------------------------------------------------------

Тест 4: SearchFunctionalityOnMainMenu: Търсене на потребител през основното меню

Цел на теста:
Тестът проверява дали функционалността за търсене на потребители през основното меню работи коректно. Тестът включва различни случаи на търсене, включително чувствителност към регистъра на буквите и търсене с непълни данни.

Стъпки на теста:
1. testSearchUser():
    - Търсене по пълно потребителско име и проверка за видимост на резултатите.
2. testClickOnSearchResult():
    - Търсене по пълно потребителско име, кликване върху резултата и проверка за пренасочване към правилния профил.
3. testCaseInsensitiveSearch():
    - Търсене с комбинация от малки и главни букви и проверка за правилни резултати.
4. testPartialSearch():
    - Търсене с част от потребителското име и проверка дали резултатите съдържат правилния потребител.

Очакван резултат:
- Резултатите от търсенето трябва да се показват коректно, независимо от формата на буквите.
- Потребителят трябва да бъде насочен към правилната профилна страница при кликване върху резултата от търсенето.
- Търсенето трябва да работи и при непълни въведени данни.
--------------------------------------------------------------------------------------------

Тест 5: UploadFileTest: Качване на файл

Цел на теста:
Тестът проверява дали функционалността за качване на файл в приложението работи правилно. Тестът включва качване на файл, добавяне на описание и публикуване на поста.

Стъпки на теста:
1. testUploadFile():
    - Клика върху линка за създаване на нов пост.
    - Изчаква да се появи заглавието на страницата за качване.
    - Качва тестовия файл от зададения път.
    - Изчаква да се визуализира каченият файл.
    - Въвежда описание на файла и публикува поста.
    - Проверява дали се появява съобщение за успешното създаване на поста.

Очакван резултат:
- Заглавието на страницата за качване трябва да бъде видимо.
- Каченият файл трябва да се визуализира правилно.
- Постът трябва да бъде успешно публикуван и да се появи съобщение за успешното му създаване.



