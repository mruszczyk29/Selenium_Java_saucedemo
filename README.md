# Testy Automatyczne dla Saucedemo.com

Ten projekt mały zawiera zestaw testów automatycznych, które zostały napisane w języku Java z wykorzystaniem frameworka Selenium. Testy mają na celu sprawdzenie funkcjonalności strony internetowej Saucedemo.com, która jest platformą e-commerce sprzedająca różne produkty.

# Opis testów

* **LoginTest:** Testuje proces logowania na stronie Saucedemo.com. Sprawdza, czy użytkownik może zalogować się poprawnymi danymi logowania oraz czy po zalogowaniu menu jest widoczne. Dodatkowo, test sprawdza, czy po podaniu błędnych danych logowania pojawia się odpowiedni komunikat błędu.

* **LogoutTest:** Testuje proces wylogowania użytkownika. Sprawdza, czy po zalogowaniu użytkownik może poprawnie się wylogować, a po wylogowaniu przekierowany zostaje na stronę logowania.

* **SortItemTest:** Testuje sortowanie produktów na stronie Saucedemo.com. Testy sprawdzają poprawność sortowania produktów pod względem ceny (od najniższej do najwyższej i od najwyższej do najniższej) oraz pod względem nazwy (od A do Z i od Z do A).

* **CompleteOrderTest:** Testuje proces składania zamówienia na stronie Saucedemo.com. Sprawdza, czy użytkownik może dodać produkty do koszyka, przejść do procesu płatności, wprowadzić dane płatności i złożyć zamówienie. Test również weryfikuje, czy po złożeniu zamówienia pojawia się odpowiednie potwierdzenie.

* **CartTest:** Testuje funkcjonalność koszyka na stronie Saucedemo.com. Sprawdza, czy użytkownik może dodać produkty do koszyka, sprawdzić zawartość koszyka, usunąć produkty z koszyka. Test również sprawdza poprawność wyświetlania ilości dodanych pozycji do koszyka.

# Dodatkowe informacje

* Testy automatyczne są napisane przy użyciu języka Java i frameworka Selenium, który umożliwia automatyzację testów interfejsu użytkownika dla stron internetowych.
* Testy generują zrzuty ekranu po każdym teście, które są zapisywane w folderze screenshots w odpowiednich podfolderach dla każdego testu.
* Testy wykorzystują przeglądarkę Chrome, dlatego przed uruchomieniem upewnij się, że masz zainstalowaną przeglądarkę Chrome oraz odpowiedni WebDriver (ChromeDriver).
