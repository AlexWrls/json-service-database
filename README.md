### Cервис работы с данными в БД

---
> **Условия**


 - **_Входные параметры_**, аргументы командной строки где указывается тип операции, имя входного и имя выходного файла;


 - **_Входной файл_** - набор аргументов в формате json для извлечения данных из БД и последующей записи в выходной файл ;
 
> **Результат**

 - **_Выходной файл_** - содержит извлеченнные из БД данные в формате json;
     - Поиск покупателей по критериям (search)
     - Сбор статистики за период (stat)
 - Возможные ошибки обработаны и зафиксированы в выходном файле;
    - реализовано с помощю `try(){}catch()` с последующей зиписью в **_выходной файл_**
    
> **Используемый стек**
  
   - Язык программирования [_**Java 8**_](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html "_**Java 8**_");

   - База данных [_**Postgresql**_](https://www.enterprisedb.com/downloads/postgres-postgresql-downloads "_**Postgresql**_");


   - Сборщик проекта [_**Maven**_](http://maven.apache.org "_**Maven**_");   
         
 ---
 
| Инструкция  | 
| ------------ | 
 
 - Параметры программы задаются при запуске через аргументы командной строки, по порядку:
   1. тип выполняемой операции ;
         - [`-search`] - поиск покупателей по критериям 
         - [`-stat`] - сбор статистики за период 
   1. Имя входного файла;   
         - [`input.json`] -  файл должен находится в директории `/src/main/resources/source/`
   1. Имя выходного файла;
         - [`output.json`] -  файл будет записан в директорию `/src/main/resources/result/`
         
  #### Search
- Примеры запуска из командной строки для Windows:
  - для [`-search`] - поиск покупателей по критериям <br>
  аргумены:
`-search inputSearch.json output.json`

| inputSearch.json  | 
| ------------ | 
    {
      "criterias": [
        {"lastName": "Иванов"},
        {"productName": "Минеральная вода", "minTimes": 2},
        {"minExpenses": 112, "maxExpenses": 4000},
        {"badCustomers": 3}
      ]
    }
| output.json  | 
| ------------ | 
    {
      "type": "search",
      "result": [
        {
          "criteria": {
            "lastName": "Иванов"
          },
          "result": [
            {
              "firstName": "Иван",
              "lastName": "Иванов"
            },
            {
              "firstName": "Петр",
              "lastName": "Иванов"
            }
          ]
        },
        {
          "criteria": {
            "productName": "Минеральная вода",
            "minTimes": 2.0
          },
          "result": [
            {
              "firstName": "Петр",
              "lastName": "Иванов"
            }
          ]
        },
        {
          "criteria": {
            "minExpenses": 112.0,
            "maxExpenses": 4000.0
          },
          "result": [
            {
              "firstName": "Анна",
              "lastName": "Кузнецова"
            }
          ]
        },
        {
          "criteria": {
            "badCustomers": 3.0
          },
          "result": [
            {
              "firstName": "Анна",
              "lastName": "Кузнецова"
            },
            {
              "firstName": "Петр",
              "lastName": "Иванов"
            },
            {
              "firstName": "Иван",
              "lastName": "Иванов"
            }
          ]
        }
      ]
    }
      
#### Stat
- Примеры запуска из командной строки для Windows:
   - для [`-stat`] - поиск покупателей по критериям <br>
   аргумены:
`-stat inputStat.json output.json`

| inputSearch.json  | 
| ------------ |  
    {
      "startDate": "2021-03-18",
      "endDate": "2021-03-22"
    }
    
| output.json  | 
| ------------ |

    {
      "type": "stat",
      "totalDays": 3,
      "customers": [
        {
          "name": "Иван Иванов",
          "purchases": [
            {
              "name": "молоко",
              "expenses": 75
            },
            {
              "name": "масло",
              "expenses": 70
            },
            {
              "name": "Минеральная вода",
              "expenses": 45
            },
            {
              "name": "хлеб",
              "expenses": 35
            }
          ],
          "totalExpenses": 225
        },
        {
          "name": "Анна Кузнецова",
          "purchases": [
            {
              "name": "сыр",
              "expenses": 120
            },
            {
              "name": "масло",
              "expenses": 70
            }
          ],
          "totalExpenses": 190
        },
        {
          "name": "Петр Иванов",
          "purchases": [
            {
              "name": "Минеральная вода",
              "expenses": 90
            }
          ],
          "totalExpenses": 90
        }
      ],
      "totalExpenses": 505,
      "avgExpenses": 168.34
    }
    
#### Error
- Примеры запуска из командной строки для Windows:
   - для имитация ошибки <br>
   аргумены:
`-stat inputErr.json output.json`

| inputSearch.json  | 
| ------------ |  
    {
      "startDate": "2021/03/18",
      "endDate": "2021-03-22"
    }
    
| output.json  | 
| ------------ |
    
    {
      "type": "error",
      "message": "Неправильный формат даты"
    }
