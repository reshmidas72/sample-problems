import pygame
import random
import sys

# Initialize pygame
pygame.init()

# Screen setup
WIDTH, HEIGHT = 800, 400
screen = pygame.display.set_mode((WIDTH, HEIGHT))
pygame.display.set_caption("Dinosaur Game 🦖")

# Colors
WHITE = (255, 255, 255)
BLACK = (0, 0, 0)
GREEN = (34, 177, 76)

# Game variables
clock = pygame.time.Clock()
font = pygame.font.SysFont(None, 40)

# Dinosaur setup
dino_width, dino_height = 50, 50
dino_x, dino_y = 50, HEIGHT - dino_height - 50
dino_y_velocity = 0
gravity = 1
is_jumping = False

# Obstacle setup
obstacle_width, obstacle_height = 40, 60
obstacle_x = WIDTH
obstacle_y = HEIGHT - obstacle_height - 50
obstacle_speed = 7

# Score
score = 0

def draw_dino(x, y):
    pygame.draw.rect(screen, GREEN, (x, y, dino_width, dino_height))

def draw_obstacle(x, y):
    pygame.draw.rect(screen, BLACK, (x, y, obstacle_width, obstacle_height))

def show_text(text, x, y):
    label = font.render(text, True, BLACK)
    screen.blit(label, (x, y))

def game_loop():
    global dino_y, dino_y_velocity, is_jumping, obstacle_x, score, obstacle_speed

    running = True
    score = 0

    while running:
        screen.fill(WHITE)

        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                pygame.quit()
                sys.exit()

            if event.type == pygame.KEYDOWN:
                if event.key == pygame.K_SPACE and not is_jumping:
                    dino_y_velocity = -15
                    is_jumping = True

        # Dinosaur physics
        dino_y += dino_y_velocity
        dino_y_velocity += gravity

        if dino_y >= HEIGHT - dino_height - 50:
            dino_y = HEIGHT - dino_height - 50
            is_jumping = False

        # Obstacle movement
        obstacle_x -= obstacle_speed
        if obstacle_x < -obstacle_width:
            obstacle_x = WIDTH
            score += 1
            obstacle_speed += 0.5  # Increase speed as score increases

        # Collision detection
        if (dino_x < obstacle_x + obstacle_width and
            dino_x + dino_width > obstacle_x and
            dino_y + dino_height > obstacle_y):
            show_text("💀 Game Over!", WIDTH//2 - 100, HEIGHT//2)
            pygame.display.update()
            pygame.time.delay(2000)
            game_loop()  # Restart game

        # Draw objects
        draw_dino(dino_x, dino_y)
        draw_obstacle(obstacle_x, obstacle_y)
        show_text("Score: " + str(score), 10, 10)

        pygame.display.update()
        clock.tick(30)


