$ErrorActionPreference = 'Stop'

$gradleVersion = '9.6.0'
$gradleRoot = Join-Path $PSScriptRoot '.gradle-dist'
$gradleHome = Join-Path $gradleRoot "gradle-$gradleVersion"
$gradleExe = Join-Path $gradleHome 'bin\gradle.bat'
$archive = Join-Path $gradleRoot "gradle-$gradleVersion-bin.zip"

if (-not (Test-Path $gradleExe)) {
    New-Item -ItemType Directory -Force -Path $gradleRoot | Out-Null
    $url = "https://services.gradle.org/distributions/gradle-$gradleVersion-bin.zip"
    Write-Host "Descargando Gradle $gradleVersion..."
    Invoke-WebRequest -Uri $url -OutFile $archive
    Expand-Archive -LiteralPath $archive -DestinationPath $gradleRoot -Force
    Remove-Item -LiteralPath $archive -Force
}

& $gradleExe --no-daemon clean build
if ($LASTEXITCODE -ne 0) {
    exit $LASTEXITCODE
}

Write-Host "`nBuild terminado. Busca el JAR en: $PSScriptRoot\build\libs\"
Get-ChildItem (Join-Path $PSScriptRoot 'build\libs') -Filter '*.jar' | Select-Object -ExpandProperty FullName
