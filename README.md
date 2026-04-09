# Custom Code Block

Jahia module providing a `jnt:customCodeBlock` component that allows editors to inject raw HTML, CSS and JavaScript directly into a page.

## Requirements

- Jahia 8.2.0.0+
- Java 11
- [CodeMirror Editor](https://store.jahia.com/contents/modules-repository/org/jahia/se/modules/codemirror-editor.html) module

## Node type

```
[jnt:customCodeBlock] > jnt:content, jmix:advancedContent
 - html (string) i18n
 - css (string)
 - js (string)
```

| Property | Type   | i18n | Description                                                                 |
|----------|--------|------|-----------------------------------------------------------------------------|
| `html`   | String | yes  | Directly injected on the page as-is (HTML, script tags, embeds...)          |
| `css`    | String | no   | Injected in the `<head>` wrapped in a `<style>` tag                         |
| `js`     | String | no   | JS code only (no script tags) — injected before end of `<body>` in a `<script>` tag |

`html` is internationalised so each language can have its own markup. `css` and `js` are shared across languages.

## Content Editor

The three fields use the **CodeMirror** selector type for syntax highlighting:

- `html` → default mode
- `css` → CSS mode
- `js` → JavaScript mode

Form overrides are declared in `META-INF/jahia-content-editor-forms/fieldsets/jcnt_customCodeBlock.json`.

## Build & Deployment

```bash
mvn clean package
```

Upload `target/custom-code-block-*.jar` via **Jahia Administration → Modules & Extensions → Modules**.

Once installed, the module must be enabled per site — go to each site's settings and activate it from the modules list.

The component will then be available in the **Advanced Content** category when adding content in page edit mode.
