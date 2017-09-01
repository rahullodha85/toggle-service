from setuptools import setup

setup(
    name='toggle-validation',
    version='0.4',
    install_requires=['python-dateutil', 'pytz', 'requests'],
    scripts=['toggle-validation.py'],
    packages=['rules']
)
