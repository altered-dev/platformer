# Платформер (пока без названия)

Этот проект более технический, нежели сюжетный, поэтому здесь будут описаны в основном лишь различные игровые механики.

## Мотивация

На рынке есть некоторое количество классных платформеров с редакторами уровней, и несмотря на то, что они выполняют свою
задачу, есть два направления, куда они, к сожалению, падают:

- Редактор очень простой для понимания, но его возможности очень ограничены и порой даже навязывают определенный стиль.
  К таким относятся Super Mario Maker, I Wanna Maker;
- У игроков буквально развязаны руки в плане креативности, возможно создать практически что угодно, но сам редактор при
  этом сложный ввиду не интуитивного интерфейса и/или перенасыщения функциями. К таким относятся Geometry Dash,
  BattleBlock Theater;
- Бонус! В игре нет редактора, но игроки добавили его собственными руками через моды, либо пишут уровни через код. К
  таким относятся Celeste, Sonic Generations, Super Meat Boy.

В этом проекте планируется собрать все плюсы этих редакторов уровней, а также привлечь некоторые другие механики из
другого рода программного обеспечения, которые могут помочь создать интуитивный и мощный редактор.

## Что уникального есть в этой игре?

Редактор уровней позволит реализовать уровень практически любого стиля с практически любыми механиками, не требуя знаний
в программировании. Для этого будет реализовано несколько основных механик:

- Возможность создания простых (прямоугольник, овал) и произвольных фигур с плоской, градиентной и текстурной заливкой;
- Добавление коллизий с этими объектами;
- Создание различных других игровых объектов, которые будут взаимодействовать с игроком определенным образом, для
  реализации множества игровых механик;
- Возможность изменения свойств объектов (позиция, вращение, размер, цвет, вершины многоугольников и т.д.)с помощью
  ключевых кадров, анимация между ключевыми кадрами с помощью набора сглаживающих функций (easing);
- Добавление действий (actions) (изменение свойств объектов, проигрывание звука, убийство персонажа и т.д.), привязанных
  к различным триггерам (столкновение с объектом, достижение определенного времени и т.д.). Действия могут быть
  составными и редактируются в собственном редакторе (а-ля Scratch).

## Содержание

- [Основные механики](basics.md)
- [Управление (там пока пусто)](controls.md)
- [Возможности редактора](editor.md)
- [Сюжетная кампания](campaign.md)
- [Онлайн-уровни](online.md)
